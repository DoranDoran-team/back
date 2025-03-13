package com.korit.dorandoran.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korit.dorandoran.entity.SubscriptionEntity;
import com.korit.dorandoran.entity.subscription_pk.SubscriptionPK;
import java.util.List;


@Repository
public interface SubscribtionRepository extends JpaRepository<SubscriptionEntity, SubscriptionPK>{
    List<SubscriptionEntity> findByUserId(String userId);

    SubscriptionEntity findBySubscriberAndUserId(String userId, String subscriber);

    List<SubscriptionEntity> findBySubscriber(String subscriber);
}
