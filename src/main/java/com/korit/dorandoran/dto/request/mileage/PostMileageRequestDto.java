package com.korit.dorandoran.dto.request.mileage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostMileageRequestDto {

    @NotNull    
    private Integer amount;  // 금액

    @NotBlank
    private String accountNumber;  // 계좌 번호

    @NotBlank
    private String bankName;  // 은행명

    private String userId;
}