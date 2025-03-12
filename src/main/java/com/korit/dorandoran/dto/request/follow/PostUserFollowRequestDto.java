package com.korit.dorandoran.dto.request.follow;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostUserFollowRequestDto {
    private String userId;
    private String subscriber;
}
