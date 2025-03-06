package com.korit.dorandoran.service;

import org.springframework.http.ResponseEntity;

import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.like.GetLikeListResponseDto;

public interface LikesService {
    
    ResponseEntity<ResponseDto> postLike(Integer targetId, String userId, String likeTypeStr);

    ResponseEntity<ResponseDto> deleteLike(Integer targetId, String userId, String likeTypeStr);

    ResponseEntity<? super GetLikeListResponseDto> getLikeList(Integer roomId, String userId);
}
