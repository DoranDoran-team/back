package com.korit.dorandoran.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.korit.dorandoran.dto.request.postDiscussion.PostDiscussionWriteRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.service.DiscussionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/v1/gen_disc")
@RequiredArgsConstructor
public class DiscussionController {
    
    private final DiscussionService discussionService;

    @PostMapping("/write")
    public ResponseEntity<ResponseDto> writeDiscussion (
        @RequestBody @Valid PostDiscussionWriteRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> responseBody = discussionService.postDiscussionWite(requestBody);
        return responseBody;
    } 
}
