package com.korit.dorandoran.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import com.korit.dorandoran.common.object.ChatRoomStatus;
import com.korit.dorandoran.entity.chatting.ChatRoom;
import com.korit.dorandoran.repository.chatting.ChatRoomRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatRoomScheduler {

    private final ChatRoomRepository chatRoomRepository;

    @Scheduled(fixedRate = 60000)
    public void updateChatRoomStatus() {
        LocalDateTime now = LocalDateTime.now();
        List<ChatRoom> waitingRooms = chatRoomRepository.findByStatus(ChatRoomStatus.WAITING);
        for (ChatRoom room : waitingRooms) {
            if (room.getScheduledTime() != null && room.getScheduledTime().minusMinutes(15).isBefore(now)) {
                room.setStatus(ChatRoomStatus.PREPARING);
                chatRoomRepository.save(room);
            }
        }
    }
}