package com.korit.dorandoran.entity;

import com.korit.dorandoran.common.object.NotificationType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;

    private String userId;

    private boolean isRead = false;

    private String message;

    private String notificationDate;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    public enum NotificationType {
        MILEAGE_EARNED, REFUND_APPROVED, REFUND_DENIED, NEW_REFUND_REQUEST, REFUND_REQUESTED
    }

    public NotificationEntity(String userId, String message, NotificationType notificationType, String notificationDate) {
        this.userId = userId;
        this.isRead = false;
        this.message = message;
        this.notificationType = notificationType;
        this.notificationDate = notificationDate;
    }
}
