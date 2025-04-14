package com.korit.dorandoran.service;

import java.util.List;

import com.korit.dorandoran.entity.chatting.ChatMessage;
import com.korit.dorandoran.entity.chatting.ChatReservation;
import com.korit.dorandoran.entity.chatting.ChatRoom;

public interface ChatRoomService {
    ChatRoom createChatRoom(ChatRoom chatRoom);
    ChatReservation reserveChatRoom(String roomId, List<String> userIds);
    void cancelReservation(String roomId, String userId);
    void confirmReservation(String roomId);
    ChatMessage saveChatMessage(ChatMessage message);
    List<ChatMessage> getMessagesByRoom(String roomId);
    List<ChatRoom> getAllChatRooms();
    void endChatRoom(String roomId);
    ChatRoom getChatRoomById(String roomId);
}
