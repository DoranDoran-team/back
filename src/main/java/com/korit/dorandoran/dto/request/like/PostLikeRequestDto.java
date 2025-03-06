package com.korit.dorandoran.dto.request.like;

import com.korit.dorandoran.common.object.LikeType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostLikeRequestDto {
    
    @NotNull
    private Integer targetId;
    @NotBlank 
    private String userId;
    @NotNull
    private LikeType likeType;
}
