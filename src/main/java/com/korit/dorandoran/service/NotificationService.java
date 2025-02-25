package com.korit.dorandoran.service;

import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.notification.NotificationResponseDto;
import com.korit.dorandoran.entity.NotificationEntity.NotificationType;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface NotificationService {
    ResponseEntity<ResponseDto> createNotification(String userId, String message, NotificationType notificationType);
    ResponseEntity<List<NotificationResponseDto>> getUserNotifications(String userId);
    ResponseEntity<Void> markAsRead(Integer notificationId);
}
