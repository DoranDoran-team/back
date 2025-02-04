package com.korit.dorandoran.dto.request.mypage.myInfo;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchProfileRequestDto {

    @NotBlank
    private String nickName;

    private String statusMessage;

    private String profileImage;
    
}
