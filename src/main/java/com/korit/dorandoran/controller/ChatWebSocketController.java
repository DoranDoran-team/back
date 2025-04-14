package com.korit.dorandoran.controller;

import com.korit.dorandoran.entity.chatting.ChatMessage;
import com.korit.dorandoran.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage chatMessage) {
        ChatMessage savedMessage = chatRoomService.saveChatMessage(chatMessage);
        String destination = "/topic/chat/" + chatMessage.getRoomId();
        messagingTemplate.convertAndSend(destination, savedMessage);
    }
}
