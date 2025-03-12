package com.korit.dorandoran.service.implement.mileage;

import com.korit.dorandoran.dto.request.mileage.PostAdminMileageRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.mileage.MileageRequestDto;
import com.korit.dorandoran.entity.mileage.AdminMileageEntity;
import com.korit.dorandoran.entity.mileage.MileageEntity;
import com.korit.dorandoran.entity.NotificationEntity;
import com.korit.dorandoran.entity.UserEntity;
import com.korit.dorandoran.entity.NotificationEntity.NotificationType;
import com.korit.dorandoran.repository.mileage.AdminMileageRepository;
import com.korit.dorandoran.repository.mileage.MileageRepository;
import com.korit.dorandoran.repository.UserRepository;
import com.korit.dorandoran.service.mileage.AdminMileageService;
import com.korit.dorandoran.service.NotificationService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminMileageServiceImplement implements AdminMileageService {

    private final AdminMileageRepository adminMileageRepository;
    private final MileageRepository mileageRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService; // 알림 서비스 추가

    // 관리자 마일리지 지급 (알림 추가)
    @Override
    public ResponseEntity<ResponseDto> giveMileage(PostAdminMileageRequestDto requestDto) {
        try {
            UserEntity user = userRepository.findByUserId(requestDto.getUserId());

            if (user == null) {
                return ResponseEntity.badRequest().body(new ResponseDto("ER", "존재하지 않는 사용자 ID입니다."));
            }

            user.setMileage(user.getMileage() + requestDto.getAmount());
            userRepository.save(user);

            // 지급 내역 저장 (admin_mileage 테이블)
            adminMileageRepository.save(new AdminMileageEntity(requestDto));

            // 마일리지 지급 알림 전송
            String message = requestDto.getReason() + "으로 " + requestDto.getAmount() + "p가 지급되었습니다.";
            notificationService.createNotification(user.getUserId(), message, NotificationType.MILEAGE_EARNED, "");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    // 모든 환급 요청 조회
    @Override
    public ResponseEntity<List<MileageRequestDto>> getRefundRequests() {
        List<MileageEntity> refundRequests = mileageRepository.findAllByOrderByTransactionDateAsc();

        List<MileageRequestDto> responseList = refundRequests.stream()
                .map(MileageRequestDto::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    // 환급 요청 승인/반려 처리 (알림 추가)
    @Override
    public ResponseEntity<ResponseDto> updateRefundStatus(Integer mileageId, String status) {
        MileageEntity mileage = mileageRepository.findById(mileageId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 환급 신청입니다."));

        if (!mileage.getStatus().equals("승인 대기")) {
            return ResponseEntity.badRequest().body(new ResponseDto("ER", "이미 처리된 요청입니다."));
        }

        mileage.setStatus(status);
        mileageRepository.save(mileage);

        // 승인된 경우, 해당 유저의 마일리지 차감
        if (status.equals("승인")) {
            UserEntity user = userRepository.findByUserId(mileage.getUserId());
            if (user != null) {
                user.setMileage(user.getMileage() - mileage.getAmount());
                userRepository.save(user);
            }
        }

        // 환급 승인/거절 알림 전송
        notificationService.createNotification(
                mileage.getUserId(),
                status.equals("승인")
                        ? mileage.getAmount() + "p 환급 요청이 승인되었습니다."
                        : mileage.getAmount() + "p 환급 요청이 거절되었습니다.",
                status.equals("승인")
                        ? NotificationEntity.NotificationType.REFUND_APPROVED
                        : NotificationEntity.NotificationType.REFUND_DENIED,
                "");

        return ResponseDto.success();
    }

    // 생일 마일리지 지급: 모든 사용자를 조회하여 생일인 경우 1000p 지급 및 알림 전송
    @Override
    public ResponseEntity<ResponseDto> awardBirthdayBonus() {
        // 내부 로직 재사용
        awardBirthdayBonusInternal();
        return ResponseDto.success();
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void awardBirthdayBonusInternal() {
        try {
            LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
            String todayMMDD = String.format("%02d%02d", today.getMonthValue(), today.getDayOfMonth());

            List<UserEntity> users = userRepository.findAll();
            for (UserEntity user : users) {
                if (user.getBirth() != null && user.getBirth().length() >= 8) {
                    String userBirthMMDD = user.getBirth().substring(4, 8);
                    if (userBirthMMDD.equals(todayMMDD)) {
                        PostAdminMileageRequestDto birthdayDto = new PostAdminMileageRequestDto();
                        birthdayDto.setUserId(user.getUserId());
                        birthdayDto.setAmount(1000);
                        birthdayDto.setReason("생일 축하금 지급");
                        birthdayDto.setCustomReason("");
                        adminMileageRepository.save(new AdminMileageEntity(birthdayDto));

                        String message = "생일 축하합니다! 당신의 생일을 맞아 1000p의 생일 마일리지가가 지급되었습니다.";
                        notificationService.createNotification(user.getUserId(), message, NotificationType.BIRTHDAY,
                                "/mypage/mileage");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
