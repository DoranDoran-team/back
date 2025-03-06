package com.korit.dorandoran.entity.pk;

import java.io.Serializable;

import com.korit.dorandoran.common.object.LikeType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikesPK implements Serializable{

    @Column(name="target_id")
    private Integer targetId;
    @Column(name="user_id")
    private String userId;
    @Enumerated(EnumType.STRING)
    @Column(name="like_type")
    private LikeType likeType;
    
}
