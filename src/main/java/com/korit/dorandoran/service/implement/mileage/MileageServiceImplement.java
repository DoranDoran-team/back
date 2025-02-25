package com.korit.dorandoran.service.implement.mileage;

import com.korit.dorandoran.dto.request.mileage.PostMileageRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.mileage.EarningHistoryDto;
import com.korit.dorandoran.dto.response.mileage.GetMileageResponseDto;
import com.korit.dorandoran.dto.response.mileage.RefundHistoryDto;
import com.korit.dorandoran.entity.mileage.AdminMileageEntity;
import com.korit.dorandoran.entity.mileage.MileageEntity;
import com.korit.dorandoran.entity.NotificationEntity;
import com.korit.dorandoran.entity.UserEntity;
import com.korit.dorandoran.entity.NotificationEntity.NotificationType;
import com.korit.dorandoran.repository.UserRepository;
import com.korit.dorandoran.repository.mileage.AdminMileageRepository;
import com.korit.dorandoran.repository.mileage.MileageRepository;
import com.korit.dorandoran.service.mileage.MileageService;
import com.korit.dorandoran.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MileageServiceImplement implements MileageService {

    private final MileageRepository mileageRepository;
    private final AdminMileageRepository adminMileageRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository; // 관리자 조회를 위한 추가

    // [기능 1] 환급 요청 처리 및 알림 추가
    @Override
    public ResponseEntity<ResponseDto> requestRefund(PostMileageRequestDto requestDto, String userId) {
        try {
            MileageEntity mileageEntity = new MileageEntity(requestDto, userId);
            mileageRepository.save(mileageEntity);

            // 사용자에게 환급 요청 알림 전송
            notificationService.createNotification(
                    userId,
                    "환급 요청이 접수되었습니다. 승인 여부를 확인하세요.",
                    NotificationEntity.NotificationType.REFUND_REQUESTED);

            // 관리자(role == 1)에게 새로운 환급 요청 알림 전송
            List<UserEntity> admins = userRepository.findByRole(true);
            for (UserEntity admin : admins) {
                notificationService.createNotification(
                        admin.getUserId(),
                        "새로운 환급 요청이 들어왔습니다.",
                        NotificationEntity.NotificationType.NEW_REFUND_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    // [기능 2] 사용자의 마일리지 정보 조회
    @Override
    public ResponseEntity<GetMileageResponseDto> getMileageData(String userId) {
        List<MileageEntity> mileageList = mileageRepository.findAllByUserId(userId);
        List<AdminMileageEntity> earningList = adminMileageRepository.findAllByUserId(userId);

        Integer totalReceivedMileage = adminMileageRepository.getTotalReceivedMileageByUserId(userId);
        if (totalReceivedMileage == null)
            totalReceivedMileage = 0;

        Integer totalRefundedMileage = mileageRepository.getTotalRequestedMileageByUserIdAndStatus(userId, "승인");
        if (totalRefundedMileage == null)
            totalRefundedMileage = 0;

        Integer pendingRefundMileage = mileageRepository.getTotalRequestedMileageByUserIdAndStatus(userId, "승인 대기");
        if (pendingRefundMileage == null)
            pendingRefundMileage = 0;

        int availableMileage = totalReceivedMileage - totalRefundedMileage - pendingRefundMileage;

        List<RefundHistoryDto> refundHistory = new ArrayList<>();
        for (MileageEntity mileage : mileageList) {
            refundHistory
                    .add(new RefundHistoryDto(mileage.getTransactionDate(), mileage.getAmount(), mileage.getStatus()));
        }

        List<EarningHistoryDto> earningHistory = new ArrayList<>();
        for (AdminMileageEntity earning : earningList) {
            earningHistory.add(new EarningHistoryDto(earning.getGivenDate(), earning.getReason(), earning.getAmount()));
        }

        return GetMileageResponseDto.success(
                totalReceivedMileage,
                totalRefundedMileage,
                availableMileage,
                refundHistory,
                earningHistory);
    }

    // [기능 3] 환급 요청 승인/반려
    public void updateRefundStatus(String userId, Integer mileageId, String status) {
        MileageEntity mileageEntity = mileageRepository.findById(mileageId)
                .orElseThrow(() -> new RuntimeException("환급 요청을 찾을 수 없습니다."));
        mileageEntity.setStatus(status);
        mileageRepository.save(mileageEntity);

        // 환급 승인/거절 시 사용자에게 알림 전송
        notificationService.createNotification(
                mileageEntity.getUserId(),
                status.equals("승인") ? mileageEntity.getAmount() + "p 환급 요청이 승인되었습니다."
                        : mileageEntity.getAmount() + "p 환급 요청이 거절되었습니다.",
                status.equals("승인") ? NotificationType.REFUND_APPROVED
                        : NotificationEntity.NotificationType.REFUND_DENIED);
    }

    // [기능 4] 마일리지 지급 및 알림 전송
    public void giveMileage(String userId, Integer amount, String reason) {
        AdminMileageEntity adminMileageEntity = new AdminMileageEntity();
        adminMileageEntity.setUserId(userId);
        adminMileageEntity.setAmount(amount);
        adminMileageEntity.setReason(reason);
        adminMileageRepository.save(adminMileageEntity);

        // 마일리지 지급 시 사용자에게 알림 전송 (사유 포함)
        notificationService.createNotification(
                userId,
                reason + "으로 " + amount + "p가 지급되었습니다.",
                NotificationType.MILEAGE_EARNED);
    }
}
