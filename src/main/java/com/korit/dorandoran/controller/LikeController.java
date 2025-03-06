package com.korit.dorandoran.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.like.GetLikeListResponseDto;
import com.korit.dorandoran.service.LikesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/like")
@RequiredArgsConstructor
public class LikeController {
    
    private final LikesService likeService;

    @PostMapping("{targetId}/{likeTypeStr}")
    public ResponseEntity<ResponseDto> postLike(
        @PathVariable("targetId") Integer targetId,
        @PathVariable("likeTypeStr") String likeTypeStr,
        @AuthenticationPrincipal String userId
    ){
        ResponseEntity<ResponseDto> responseBody = likeService.postLike(targetId, userId, likeTypeStr);
        return responseBody;
    }

    @DeleteMapping("{targetId}/{likeTypeStr}")
    public ResponseEntity<ResponseDto> deleteLike(
        @PathVariable("targetId") Integer targetId,
        @PathVariable("likeTypeStr") String likeTypeStr,
        @AuthenticationPrincipal String userId
    ){
        ResponseEntity<ResponseDto> responseBody = likeService.deleteLike(targetId, userId, likeTypeStr);
        return responseBody;
    }

    @GetMapping("{roomId}")
    public ResponseEntity<? super GetLikeListResponseDto> getLike(
        @PathVariable("roomId") Integer roomId,
        @AuthenticationPrincipal String userId
    ){
        ResponseEntity<? super GetLikeListResponseDto> responseBody = likeService.getLikeList(roomId, userId);
        return responseBody;
    }
}
