package com.korit.dorandoran.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korit.dorandoran.common.object.LikeType;
import com.korit.dorandoran.entity.LikesEntity;
import com.korit.dorandoran.entity.pk.LikesPK;

@Repository
public interface LikesRepository extends JpaRepository<LikesEntity, LikesPK> {
    

    boolean existsByTargetIdAndUserIdAndLikeType(Integer targetId, String userId, LikeType likeType);
}
