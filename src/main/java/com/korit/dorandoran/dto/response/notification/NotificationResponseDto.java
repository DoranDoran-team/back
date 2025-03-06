package com.korit.dorandoran.dto.response.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.korit.dorandoran.entity.NotificationEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDto {
    private Integer notificationId;
    private String userId;

    // JSON 직렬화 시 'read' 대신 'isRead'로 내려가도록 명시
    @JsonProperty("isRead")
    private boolean isRead;
    private String message;
    private String notificationDate;
    private String notificationType;
    private String additionalInfo;

    public NotificationResponseDto(NotificationEntity entity) {
        this.notificationId = entity.getNotificationId();
        this.userId = entity.getUserId();
        this.isRead = entity.isRead();
        this.message = entity.getMessage();
        this.notificationDate = entity.getNotificationDate();
        this.notificationType = entity.getNotificationType().name();
        this.additionalInfo = entity.getAdditionalInfo();
    }

    public static NotificationResponseDto fromEntity(NotificationEntity entity) {
        return new NotificationResponseDto(entity);
    }
}
