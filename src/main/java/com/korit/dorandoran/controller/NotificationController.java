package com.korit.dorandoran.controller;

import com.korit.dorandoran.dto.response.notification.NotificationResponseDto;
import com.korit.dorandoran.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mypage/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // 현재 로그인된 사용자의 알림 조회
    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> getUserNotifications(@AuthenticationPrincipal String userId) {
        if (userId == null) {
            System.out.println("🚨 @AuthenticationPrincipal is NULL! Unauthorized access.");
            return ResponseEntity.status(401).body(List.of()); // 401 Unauthorized
        }
        return notificationService.getUserNotifications(userId);
    }

    // 알림 읽음 처리 API 추가 (프론트에서 읽음 처리 요청할 때 필요)
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Integer notificationId, @AuthenticationPrincipal String userId) {
        if (userId == null) {
            System.out.println("🚨 @AuthenticationPrincipal is NULL! Unauthorized access.");
            return ResponseEntity.status(401).build();
        }
        return notificationService.markAsRead(notificationId);
    }
}
