package com.korit.dorandoran.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.korit.dorandoran.entity.AttendEntity;

@Repository
public interface AttendRepository extends JpaRepository<AttendEntity, Integer> {

    AttendEntity findByUserIdAndAttendAt(String userId, String attendAt);

    List<AttendEntity> findByUserIdAndAttendAtStartingWith(String userId, String month);
    
    List<AttendEntity> findByUserId(String userId);
}
