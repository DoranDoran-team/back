package com.korit.dorandoran.service.implement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.entity.AttendEntity;
import com.korit.dorandoran.entity.NotificationEntity.NotificationType;
import com.korit.dorandoran.entity.mileage.AdminMileageEntity;
import com.korit.dorandoran.repository.AttendRepository;
import com.korit.dorandoran.repository.mileage.AdminMileageRepository;
import com.korit.dorandoran.service.AttendanceService;
import com.korit.dorandoran.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImplement implements AttendanceService {

    private final AttendRepository attendRepository;
    private final AdminMileageRepository adminMileageRepository;
    private final NotificationService notificationService;

    @Override
    public ResponseEntity<ResponseDto> checkAttendance(String userId) {
        try {
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // 이미 출석 체크한 경우
            AttendEntity existing = attendRepository.findByUserIdAndAttendAt(userId, today);
            if (existing != null) {
                return ResponseDto.custom("ALREADY", "이미 출석 체크되었습니다.");
            }

            // 출석 기록 저장
            AttendEntity newAttend = new AttendEntity();
            newAttend.setUserId(userId);
            newAttend.setAttendAt(today);
            newAttend.setAttendStatus(1);
            attendRepository.save(newAttend);

            AdminMileageEntity adminMileage = new AdminMileageEntity();
            adminMileage.setUserId(userId);
            adminMileage.setAmount(20);
            adminMileage.setReason("출석 체크");
            adminMileage.setCustomReason(null);
            adminMileageRepository.save(adminMileage);

            String message = "출석 체크로 20p가 지급되었습니다.";
            notificationService.createNotification(userId, message, NotificationType.MILEAGE_EARNED, "/mypage");

            // 현재 달 출석 횟수 확인
            String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
            List<AttendEntity> monthlyAttendances = attendRepository.findByUserIdAndAttendAtStartingWith(userId,
                    currentMonth);
            // 만약 해당 달 출석일수가 20일이 되면 (추가 지급은 한 번만 지급해야 함 - 추가 로직 필요)
            if (monthlyAttendances.size() == 20) {
                // 추가 보너스 500p 지급
                AdminMileageEntity bonusMileage = new AdminMileageEntity();
                bonusMileage.setUserId(userId);
                bonusMileage.setAmount(500);
                bonusMileage.setReason("월 출석 20일 달성 보너스");
                bonusMileage.setCustomReason(null);
                adminMileageRepository.save(bonusMileage);

                String bonusMessage = "한 달 출석 20일 달성! 보너스로 500p가 지급되었습니다.";
                notificationService.createNotification(userId, bonusMessage, NotificationType.MILEAGE_EARNED,
                        "/mypage");
            }

            return ResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<List<AttendEntity>> getAttendanceRecords(String userId) {
        try {
            List<AttendEntity> records = attendRepository.findByUserId(userId);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
