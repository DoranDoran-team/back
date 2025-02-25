package com.korit.dorandoran.dto.response.notification;

import com.korit.dorandoran.entity.NotificationEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDto {
    private Integer notificationId;
    private String userId;
    private boolean isRead;
    private String message;
    private String notificationDate;
    private String notificationType;

    public NotificationResponseDto(NotificationEntity entity) {
        this.notificationId = entity.getNotificationId();
        this.userId = entity.getUserId();
        this.isRead = entity.isRead();
        this.message = entity.getMessage();
        this.notificationDate = entity.getNotificationDate();
        this.notificationType = entity.getNotificationType().name();
    }

    public static NotificationResponseDto fromEntity(NotificationEntity entity) {
        return new NotificationResponseDto(entity);
    }
}
