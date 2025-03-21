package com.korit.dorandoran.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import com.korit.dorandoran.entity.VoteEntity;
import com.korit.dorandoran.entity.pk.VotePK;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, VotePK> {
    
    boolean existsByRoomIdAndUserId(Integer roomId, String userId);


    @Query(value= "SELECT * FROM vote WHERE room_id=:roomId ORDER BY created_at ASC", nativeQuery = true)
    List<VoteEntity> getResult(@Param("roomId") Integer roomId);

    @Query(value= "SELECT * FROM vote WHERE room_id=:roomId", nativeQuery = true)
    List<VoteEntity> findVote(Integer roomId);


}
