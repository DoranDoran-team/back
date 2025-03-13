package com.korit.dorandoran.service;

import org.springframework.http.ResponseEntity;

import com.korit.dorandoran.dto.request.follow.PostUserFollowRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;

public interface SubscriptionService {
    ResponseEntity<ResponseDto> postSubscription(PostUserFollowRequestDto dto);

    ResponseEntity<ResponseDto> cancleFollow(String userId, String subscriber);
}
