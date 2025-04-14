package com.korit.dorandoran.repository.chatting;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.korit.dorandoran.common.object.ChatRoomStatus;
import com.korit.dorandoran.entity.chatting.ChatRoom;

import java.util.List;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    List<ChatRoom> findByStatus(ChatRoomStatus status);
}