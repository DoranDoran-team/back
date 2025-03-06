// package com.korit.dorandoran.controller;

// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.stereotype.Controller;
// import com.korit.dorandoran.dto.response.notification.NotificationResponseDto;

// import lombok.RequiredArgsConstructor;

// @Controller
// @RequiredArgsConstructor
// public class NotificationWebSocketController {

//     private final SimpMessagingTemplate messagingTemplate;

//     @MessageMapping("/sendNotification") // 클라이언트가 보낼 경로
//     @SendTo("/topic/notifications")
//     public void sendNotification(NotificationResponseDto notification) {
//         messagingTemplate.convertAndSend("/topic/notifications/" + notification.getUserId(), notification);
//     }
// }
