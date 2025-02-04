package com.korit.dorandoran.dto.request.postDiscussion;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDiscussionWriteRequestDto {
    @NotBlank
    private String userId;
    
    @NotBlank
    private String discussionType;
    @NotBlank
    private String roomTitle;
    @NotBlank
    private String roomDescription;
    private String discussionImage;

    @NotBlank
    private String agreeOpinion;
    @NotBlank 
    String oppositeOpinion;
    @NotBlank
    private String discussionEnd;

}
