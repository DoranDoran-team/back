package com.korit.dorandoran.dto.chatting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private String id;
    private String roomId;
    private String senderId;
    private String message;
    private LocalDateTime sentAt;
    private Integer speakingOrder;
    private Long speakingDuration;
}
