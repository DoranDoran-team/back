package com.korit.dorandoran.service.implement;

import com.korit.dorandoran.entity.chatting.ChatRoom;
import com.korit.dorandoran.entity.chatting.ChatMessage;
import com.korit.dorandoran.common.object.ChatRoomStatus;
import com.korit.dorandoran.entity.chatting.ChatReservation;
import com.korit.dorandoran.repository.chatting.ChatRoomRepository;
import com.korit.dorandoran.repository.chatting.ChatMessageRepository;
import com.korit.dorandoran.repository.chatting.ChatReservationRepository;
import com.korit.dorandoran.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImplement implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatReservationRepository chatReservationRepository;

    @Override
    public ChatRoom createChatRoom(ChatRoom chatRoom) {
        chatRoom.setStatus(ChatRoomStatus.WAITING);
        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public ChatReservation reserveChatRoom(String roomId, List<String> userIds) {
        ChatReservation reservation = chatReservationRepository.findByRoomId(roomId);
        if (reservation == null) {
            reservation = new ChatReservation();
            reservation.setRoomId(roomId);
            reservation.setReservedUserIds(userIds);
            reservation.setReservedAt(LocalDateTime.now());
            reservation.setConfirmed(false);
            chatReservationRepository.save(reservation);
        } else {
            List<String> existingUserIds = reservation.getReservedUserIds();
            for (String userId : userIds) {
                boolean exists = chatReservationRepository.existsByRoomIdAndReservedUserIdsContaining(roomId, userId);
                if (exists) {
                    throw new IllegalStateException("이미 예약하셨습니다. userId: " + userId);
                }
            }
            existingUserIds.addAll(userIds);
            reservation.setReservedUserIds(existingUserIds);
            reservation.setReservedAt(LocalDateTime.now());
            chatReservationRepository.save(reservation);
        }
        return chatReservationRepository.findByRoomIdAndReservedUserIdsContaining(roomId, userIds.get(0));
    }

    @Override
    public void cancelReservation(String roomId, String userId) {
        ChatReservation reservation = chatReservationRepository.findByRoomId(roomId);
        if (reservation != null && reservation.getReservedUserIds().contains(userId)) {
            reservation.getReservedUserIds().remove(userId);
            chatReservationRepository.save(reservation);
        }
    }

    @Override
    public void confirmReservation(String roomId) {
        ChatReservation reservation = chatReservationRepository.findByRoomId(roomId);
        if (reservation != null) {
            reservation.setConfirmed(true);
            chatReservationRepository.save(reservation);
        }
    }

    @Override
    public ChatMessage saveChatMessage(ChatMessage message) {
        message.setSentAt(LocalDateTime.now());
        return chatMessageRepository.save(message);
    }

    @Override
    public List<ChatMessage> getMessagesByRoom(String roomId) {
        return chatMessageRepository.findByRoomIdOrderBySentAtAsc(roomId);
    }

    @Override
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAll();
    }

    @Override
    public void endChatRoom(String roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId).orElse(null);
        if (room != null) {
            room.setStatus(ChatRoomStatus.ENDED);
            room.setEndedTime(LocalDateTime.now());
            chatRoomRepository.save(room);
        }
    }

    @Override
    public ChatRoom getChatRoomById(String roomId) {
        return chatRoomRepository.findById(roomId).orElse(null);
    }
}
