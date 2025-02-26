package com.korit.dorandoran.service;

import org.springframework.http.ResponseEntity;

import com.korit.dorandoran.dto.response.ResponseDto;

public interface LikesService {
    
    ResponseEntity<ResponseDto> postLike(Integer targetId, String userId, String likeTypeStr);
}
