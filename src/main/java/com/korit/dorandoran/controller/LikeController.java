package com.korit.dorandoran.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.korit.dorandoran.common.object.LikeType;
import com.korit.dorandoran.dto.request.like.PostLikeRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.service.LikesService;

import jakarta.validation.Valid;
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
        System.out.println("Received likeType: " + likeTypeStr);
        return responseBody;
    }
}
