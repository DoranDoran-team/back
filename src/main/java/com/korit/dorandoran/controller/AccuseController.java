package com.korit.dorandoran.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.korit.dorandoran.dto.request.accuse.PostAccuseRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.accuse.GetAccuseDetailResponseDto;
import com.korit.dorandoran.dto.response.accuse.GetAccuseListResponseDto;
import com.korit.dorandoran.dto.response.accuse.GetAccuseUserListResponseDto;
import com.korit.dorandoran.service.AccuseService;
import com.korit.dorandoran.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accuse")
public class AccuseController {

  private final AccuseService accuseService;
  private final AuthService authService;

  @PostMapping(value = { "/", "" })
  public ResponseEntity<ResponseDto> postAccuse(
      @RequestBody @Valid PostAccuseRequestDto requestBody) {
    ResponseEntity<ResponseDto> response = accuseService.postAccuse(requestBody);
    return response;
  }

  @GetMapping(value = { "/", "" })
  public ResponseEntity<? super GetAccuseListResponseDto> getAccuseList(@RequestParam("userId") String userId) {
    return accuseService.getAccuseList(userId);
  }

  @GetMapping("/{accuseId}")
  public ResponseEntity<? super GetAccuseDetailResponseDto> getAccuseDetail(
      @PathVariable("accuseId") Integer accuseId) {
    ResponseEntity<? super GetAccuseDetailResponseDto> repsonseBody = accuseService.getAccuseDetail(accuseId);
    return repsonseBody;
  }

  // 특정 유저 신고 목록 조회
  @GetMapping("/user")
  public ResponseEntity<? super GetAccuseUserListResponseDto> getAccuseUserList(
      @RequestParam("keyword") String keyword) {
    return authService.searchByNameOrUserId(keyword);
  }

  @PatchMapping("/approve/{accuseId}")
  public ResponseEntity<ResponseDto> approveAccuse(
      @PathVariable Integer accuseId) {
    return accuseService.approveAccuse(accuseId);
  }

  @PatchMapping("/rejected/{accuseId}")
  public ResponseEntity<ResponseDto> rejectedAccuse(
      @PathVariable Integer accuseId) {
    return accuseService.rejectedAccuse(accuseId);
  }
}
