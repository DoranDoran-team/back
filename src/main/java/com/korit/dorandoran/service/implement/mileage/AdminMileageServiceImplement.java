package com.korit.dorandoran.service.implement.mileage;

import com.korit.dorandoran.dto.request.mileage.PostAdminMileageRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.mileage.MileageRequestDto;
import com.korit.dorandoran.entity.mileage.AdminMileageEntity;
import com.korit.dorandoran.entity.UserEntity;
import com.korit.dorandoran.repository.mileage.AdminMileageRepository;
import com.korit.dorandoran.repository.UserRepository;
import com.korit.dorandoran.service.mileage.AdminMileageService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// 2/13 에러 주석 처리 후 merge 진행합니다. (다음 회의일 이내 해결 예정)

@Service
@RequiredArgsConstructor
public class AdminMileageServiceImplement implements AdminMileageService {

    private final AdminMileageRepository adminMileageRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseDto> giveMileage(PostAdminMileageRequestDto requestDto) {
        try {
            UserEntity user = userRepository.findByUserId(requestDto.getUserId());

            if (user == null) {
                return ResponseEntity.badRequest().body(new ResponseDto("ER", "존재하지 않는 사용자 ID입니다."));
                // 급하게 썼는데 나중에 에러 처리하러 빼겠슴다ㅎㅎ...
            }

            user.setMileage(user.getMileage() + requestDto.getAmount());
            userRepository.save(user);

            adminMileageRepository.save(new AdminMileageEntity(requestDto));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<List<MileageRequestDto>> getRefundRequests() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRefundRequests'");
    }

    @Override
    public ResponseEntity<ResponseDto> updateRefundStatus(Integer mileageId, String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRefundStatus'");
    }
}

// package com.korit.dorandoran.service.implement.mileage;

// import com.korit.dorandoran.dto.request.mileage.PostAdminMileageRequestDto;
// import com.korit.dorandoran.dto.response.ResponseDto;
// import com.korit.dorandoran.dto.response.mileage.MileageRequestDto;
// import com.korit.dorandoran.entity.mileage.AdminMileageEntity;
// import com.korit.dorandoran.entity.mileage.MileageEntity;
// import com.korit.dorandoran.entity.UserEntity;
// import com.korit.dorandoran.repository.mileage.AdminMileageRepository;
// import com.korit.dorandoran.repository.mileage.MileageRepository;
// import com.korit.dorandoran.repository.UserRepository;
// import com.korit.dorandoran.service.mileage.AdminMileageService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import java.util.List;

// @Service
// @RequiredArgsConstructor
// public class AdminMileageServiceImplement implements AdminMileageService {

// private final AdminMileageRepository adminMileageRepository;
// private final UserRepository userRepository;
// private final MileageRepository mileageRepository;

// @Override
// public ResponseEntity<ResponseDto> giveMileage(PostAdminMileageRequestDto
// dto) {
// UserEntity user = userRepository.findByUserId(dto.getUserId());
// if (user == null) return ResponseEntity.badRequest().body(new
// ResponseDto("ER", "존재하지 않는 사용자입니다."));

// adminMileageRepository.save(new AdminMileageEntity(dto));
// user.setMileage(user.getMileage() + dto.getAmount());
// return ResponseDto.success();
// }

// @Override
// @Transactional(readOnly = true)
// public ResponseEntity<List<MileageRequestDto>> getRefundRequests() {
// List<MileageRequestDto> responseList = mileageRepository
// .findAllByStatusOrderByTransactionDateAsc("승인 대기")
// .stream()
// .map(MileageRequestDto::fromEntity)
// .toList();

// return ResponseEntity.ok(responseList);
// }

// @Override
// public ResponseEntity<ResponseDto> updateRefundStatus(Integer mileageId,
// String status) {
// MileageEntity mileage = mileageRepository.findById(mileageId)
// .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 환급 신청입니다."));

// mileage.setStatus(status);
// return ResponseDto.success();
// }
// }