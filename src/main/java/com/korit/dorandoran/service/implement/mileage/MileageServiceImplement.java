package com.korit.dorandoran.service.implement.mileage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.korit.dorandoran.dto.request.mileage.PostMileageRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.mileage.GetMileageResponseDto;
import com.korit.dorandoran.dto.response.mileage.RefundHistoryDto;
import com.korit.dorandoran.entity.mileage.MileageEntity;
import com.korit.dorandoran.repository.mileage.MileageRepository;
import com.korit.dorandoran.service.mileage.MileageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MileageServiceImplement implements MileageService {

    private final MileageRepository mileageRepository;

    @Override
    public ResponseEntity<ResponseDto> requestRefund(PostMileageRequestDto requestDto, String userId) {
        try {
            MileageEntity mileageEntity = new MileageEntity(requestDto, userId);
            mileageRepository.save(mileageEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<GetMileageResponseDto> getMileageData(String userId) {
        List<MileageEntity> mileageList = mileageRepository.findAllByUserId(userId);

        int totalMileage = 0;
        int totalRefundedMileage = 0;
        List<RefundHistoryDto> refundHistory = new ArrayList<>();

        for (MileageEntity mileage : mileageList) {
            totalMileage += mileage.getTotalMileage();
            totalRefundedMileage += mileage.getUsedMileage();
            refundHistory.add(new RefundHistoryDto(mileage.getTransactionDate(), mileage.getAmount(), mileage.getStatus()));
        }

        return GetMileageResponseDto.success(totalMileage, totalRefundedMileage, refundHistory);
    }
    
    
}
