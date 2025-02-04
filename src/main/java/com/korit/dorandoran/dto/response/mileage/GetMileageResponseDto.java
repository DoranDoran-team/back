package com.korit.dorandoran.dto.response.mileage;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.korit.dorandoran.dto.response.ResponseCode;
import com.korit.dorandoran.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class GetMileageResponseDto extends ResponseDto {
    private int totalMileage;
    private int totalRefundedMileage;
    private List<RefundHistoryDto> refundHistory;

    public GetMileageResponseDto(int totalMileage, int totalRefundedMileage, List<RefundHistoryDto> refundHistory) {
        super(ResponseCode.SUCCESS, "마일리지 조회 성공");
        this.totalMileage = totalMileage;
        this.totalRefundedMileage = totalRefundedMileage;
        this.refundHistory = refundHistory;
    }

    public static ResponseEntity<GetMileageResponseDto> success(int totalMileage, int totalRefundedMileage, List<RefundHistoryDto> refundHistory) {
        return ResponseEntity.status(HttpStatus.OK).body(new GetMileageResponseDto(totalMileage, totalRefundedMileage, refundHistory));
    }
}
