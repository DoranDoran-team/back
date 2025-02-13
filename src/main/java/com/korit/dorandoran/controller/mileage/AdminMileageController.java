package com.korit.dorandoran.controller.mileage;

import com.korit.dorandoran.dto.request.mileage.PostAdminMileageRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.mileage.MileageRequestDto;
import com.korit.dorandoran.service.mileage.AdminMileageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/admin/mileage")
@RequiredArgsConstructor
public class AdminMileageController {

    private final AdminMileageService adminMileageService;

    @PostMapping("/give")
    public ResponseEntity<ResponseDto> giveMileage(
            @RequestBody @Valid PostAdminMileageRequestDto requestBody
    ) {
        return adminMileageService.giveMileage(requestBody);
    }

    // @GetMapping("/refunds")
    // public ResponseEntity<List<MileageRequestDto>> getRefundRequests() {
    //     return adminMileageService.getRefundRequests();
    // }

    // @PostMapping("/refund/{mileageId}/status")
    // public ResponseEntity<ResponseDto> updateRefundStatus(
    //         @PathVariable Integer mileageId,
    //         @RequestParam String status) {
    //     return adminMileageService.updateRefundStatus(mileageId, status);
    // }
}
