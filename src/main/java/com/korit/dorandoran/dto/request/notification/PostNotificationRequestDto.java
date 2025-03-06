package com.korit.dorandoran.dto.request.notification;

import com.korit.dorandoran.common.object.NotificationType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostNotificationRequestDto {
    private String userId;
    private String message;
    private NotificationType notificationType;
}
