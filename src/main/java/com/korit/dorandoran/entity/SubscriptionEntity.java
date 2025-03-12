package com.korit.dorandoran.entity;

import com.korit.dorandoran.dto.request.follow.PostUserFollowRequestDto;
import com.korit.dorandoran.entity.subscription_pk.SubscriptionPK;

import jakarta.persistence.Entity;
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
@Entity(name = "subscription")
@IdClass(SubscriptionPK.class)
@Table(name = "subscription")
public class SubscriptionEntity {
    
    @Id
    private String userId;

    @Id
    private String subscriber;

    public SubscriptionEntity(PostUserFollowRequestDto dto) {
        this.userId = dto.getUserId();
        this.subscriber = dto.getSubscriber();
    }
}
