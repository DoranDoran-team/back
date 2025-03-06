package com.korit.dorandoran.service;

import org.springframework.http.ResponseEntity;

import com.korit.dorandoran.dto.request.vote.PostVoteRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.vote.GetVoteResultResponseDto;

public interface VoteService {

    ResponseEntity<ResponseDto> postVote(PostVoteRequestDto dto, String userId, Integer roomId);

    ResponseEntity<? super GetVoteResultResponseDto> getVoteResult(Integer roomId);
    
}
