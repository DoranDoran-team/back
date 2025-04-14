package com.korit.dorandoran.entity.chatting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.korit.dorandoran.common.object.ChatRoomStatus;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chatRooms")
public class ChatRoom {
    @Id
    private String id;
    private String roomName;
    private List<String> proTeam;
    private List<String> conTeam;
    private ChatRoomStatus status;
    private LocalDateTime scheduledTime;
    private LocalDateTime startedTime;
    private LocalDateTime endedTime;
    private int proVoteCount;
    private int conVoteCount;
    private String representativeImage;
    private String description;

    public ChatRoom(String roomName, List<String> proTeam, List<String> conTeam, ChatRoomStatus status, LocalDateTime scheduledTime, String representativeImage, String description) {
        this.roomName = roomName;
        this.proTeam = proTeam;
        this.conTeam = conTeam;
        this.status = status;
        this.scheduledTime = scheduledTime;
        this.representativeImage = representativeImage;
        this.description = description;
        this.proVoteCount = 0;
        this.conVoteCount = 0;
    }
}
