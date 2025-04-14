package com.korit.dorandoran.dto.chatting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatReservationDto {
    private String id;
    private String roomId;
    private List<String> reservedUserIds;
    private boolean confirmed;
    private LocalDateTime reservedAt;
}