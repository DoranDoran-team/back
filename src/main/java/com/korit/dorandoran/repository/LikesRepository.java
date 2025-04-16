package com.korit.dorandoran.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korit.dorandoran.common.object.LikeType;
import com.korit.dorandoran.entity.LikesEntity;
import com.korit.dorandoran.entity.pk.LikesPK;

@Repository
public interface LikesRepository extends JpaRepository<LikesEntity, LikesPK> {
    

    boolean existsByTargetIdAndUserIdAndLikeType(Integer targetId, String userId, LikeType likeType);
    LikesEntity findByTargetIdAndUserIdAndLikeType(Integer targetId, String userId,LikeType likeType);

    @Query(value= "SELECT * FROM likes WHERE target_id=:roomId AND like_type='POST'", nativeQuery = true)
    List<LikesEntity> findLikes(@Param("roomId") Integer roomId);
}
