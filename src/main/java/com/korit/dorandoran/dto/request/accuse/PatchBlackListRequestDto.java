package com.korit.dorandoran.dto.request.accuse;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchBlackListRequestDto {

    @NotBlank
    private String userId;
}
