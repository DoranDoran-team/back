package com.korit.dorandoran.entity;

import com.korit.dorandoran.common.object.LikeType;
import com.korit.dorandoran.dto.request.like.PostLikeRequestDto;
import com.korit.dorandoran.entity.pk.LikesPK;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="likes")
@Table(name="likes")
@IdClass(LikesPK.class)
public class LikesEntity {
    
    @Id
    private Integer targetId;
    
    @Id
    private String userId;
    
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name="like_type")
    private LikeType likeType;


    @Override
    public String toString() {
        return "LikesEntity{" +
                "targetId=" + targetId +
                ", userId='" + userId + '\'' +
                ", likeType=" + likeType +
                '}';
    }

    // public LikesEntity(PostLikeRequestDto dto){
    //     this.targetId = dto.getTargetId();
    //     this.userId = dto.getUserId();
    //     this.likeType = dto.getLikeType();
    // }
}

