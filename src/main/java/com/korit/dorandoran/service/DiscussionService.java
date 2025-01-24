package com.korit.dorandoran.service;

import org.springframework.http.ResponseEntity;

import com.korit.dorandoran.dto.request.postDiscussion.PostDiscussionWriteRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;


public interface DiscussionService {
    
    ResponseEntity<ResponseDto> postDiscussionWite(PostDiscussionWriteRequestDto dto);
}
