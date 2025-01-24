package com.korit.dorandoran.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korit.dorandoran.entity.DiscussionRoomEntity;

@Repository
public interface DiscussionRoomRepository extends JpaRepository<DiscussionRoomEntity, Integer> {

    boolean existsByRoomTitle(String roomTitle);
    
}
