package com.korit.dorandoran.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.korit.dorandoran.dto.request.vote.PostVoteRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.service.VoteService;
import com.korit.dorandoran.dto.response.vote.GetVoteResultResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/vote")
@RequiredArgsConstructor
public class VoteController {
    
    private final VoteService voteService;

    @PostMapping("/{roomId}")
    public ResponseEntity<ResponseDto> postVote(
        @RequestBody @Valid PostVoteRequestDto request,
        @PathVariable("roomId") String roomId,
        @AuthenticationPrincipal String userId) {
            ResponseEntity<ResponseDto> responseBody = voteService.postVote(request, userId, roomId);
            return responseBody;
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<? super GetVoteResultResponseDto> getVoteResult(
        @PathVariable("roomId") String roomId) {
            ResponseEntity<? super GetVoteResultResponseDto> responseBody = voteService.getVoteResult(roomId);
            return responseBody;
    }
}
