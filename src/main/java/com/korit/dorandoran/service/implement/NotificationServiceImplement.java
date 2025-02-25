package com.korit.dorandoran.service.implement;

import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.notification.NotificationResponseDto;
import com.korit.dorandoran.entity.NotificationEntity;
import com.korit.dorandoran.entity.NotificationEntity.NotificationType;
import com.korit.dorandoran.repository.NotificationRepository;
import com.korit.dorandoran.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImplement implements NotificationService {

    private final NotificationRepository notificationRepository;

    // 로그인된 사용자의 알림 가져오기
    @Override
    public ResponseEntity<List<NotificationResponseDto>> getUserNotifications(String userId) {
        List<NotificationEntity> notifications = notificationRepository.findByUserId(userId);
        List<NotificationResponseDto> responseList = notifications.stream()
                .map(NotificationResponseDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    // 알림 생성 (날짜 포함)
    @Override
    public ResponseEntity<ResponseDto> createNotification(String userId, String message, NotificationType notificationType) {
        try {
            String notificationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            System.out.println("📢 알림 생성: " + message); // 디버깅 추가

            NotificationEntity notification = new NotificationEntity(userId, message, notificationType, notificationDate);
            notificationRepository.save(notification);

            return ResponseDto.success();
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseDto.databaseError();
        }
    }

    // 알림 읽음 처리 (isRead → true)
    @Override
    @Transactional
    public ResponseEntity<Void> markAsRead(Integer notificationId) {
        NotificationEntity notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 알림입니다."));
        notification.setRead(true);
        notificationRepository.save(notification);
        return ResponseEntity.ok().build();
    }
}
