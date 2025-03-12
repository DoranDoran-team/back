package com.korit.dorandoran.repository;

import com.korit.dorandoran.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {
    
    @Query("SELECT n FROM NotificationEntity n WHERE n.userId = :userId ORDER BY n.notificationDate DESC")
    List<NotificationEntity> findByUserId(@Param("userId") String userId);
}