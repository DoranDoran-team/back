package com.korit.dorandoran.dto.chatting;

import com.korit.dorandoran.common.object.ChatRoomStatus;
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
public class ChatRoomDto {
    private String id;
    private String roomName;
    private List<String> proTeam;
    private List<String> conTeam;
    private ChatRoomStatus status;
    private LocalDateTime scheduledTime;
    private int proVoteCount;
    private int conVoteCount;
    private String representativeImage;
    private String description;
}
