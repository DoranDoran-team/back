package com.korit.dorandoran.dto.request.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentRequestDto {
    
    @NotBlank
    private String userId;
    @NotBlank
    private String commentContents;
    @NotBlank
    private String discussionType;

}
