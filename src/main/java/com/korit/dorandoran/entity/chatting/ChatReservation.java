package com.korit.dorandoran.entity.chatting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chatReservations")
public class ChatReservation {
    @Id
    private String id;
    private String roomId;
    private List<String> reservedUserIds;
    private boolean confirmed;
    private LocalDateTime reservedAt;

    public ChatReservation(String roomId, List<String> reservedUserIds, LocalDateTime reservedAt) {
        this.roomId = roomId;
        this.reservedUserIds = reservedUserIds;
        this.reservedAt = reservedAt;
        this.confirmed = false;
    }
}