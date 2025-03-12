package com.korit.dorandoran.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.korit.dorandoran.dto.request.follow.PostUserFollowRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.service.SubscriptionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sub")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping(value = { "", "/" })
    public ResponseEntity<ResponseDto> postUserSubscription(
        @RequestBody @Valid PostUserFollowRequestDto requestBody
    ) {
        ResponseEntity<ResponseDto> response = subscriptionService.postSubscription(requestBody);
        return response;
    }

    @DeleteMapping("/cancle")
    public ResponseEntity<ResponseDto> cancleSubscription(
        @RequestParam("userId") String userId,
        @RequestParam("subscriber") String subscriber
    ) {
        ResponseEntity<ResponseDto> response = subscriptionService.cancleFollow(userId, subscriber);
        return response;
    }
}
