package com.korit.dorandoran.repository.chatting;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.korit.dorandoran.entity.chatting.ChatReservation;

public interface ChatReservationRepository extends MongoRepository<ChatReservation, String> {
    ChatReservation findByRoomId(String roomId);

    boolean existsByRoomIdAndReservedUserIdsContaining(String roomId, String userId);

    ChatReservation findByRoomIdAndReservedUserIdsContaining(String roomId, String userId);
}
