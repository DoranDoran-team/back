package com.korit.dorandoran.entity.chatting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chatMessages")
public class ChatMessage {
    @Id
    private String id;
    private String roomId;
    private String senderId;
    private String message;
    private String senderNickName;
    private String senderProfileImage;
    private LocalDateTime sentAt;
    private Integer speakingOrder;
    private Long speakingDuration;

    public ChatMessage(String roomId, String senderId, String message) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.message = message;
        this.senderNickName = "";
        this.senderProfileImage = "";
        this.sentAt = LocalDateTime.now();
    }
}
