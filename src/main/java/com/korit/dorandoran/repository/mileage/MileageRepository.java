package com.korit.dorandoran.repository.mileage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.korit.dorandoran.entity.mileage.MileageEntity;

public interface MileageRepository extends JpaRepository<MileageEntity, Integer> {
    List<MileageEntity> findAllByUserId(String userId);
    
}
