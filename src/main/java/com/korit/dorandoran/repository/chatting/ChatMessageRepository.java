package com.korit.dorandoran.repository.chatting;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.korit.dorandoran.entity.chatting.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByRoomIdOrderBySentAtAsc(String roomId);
}