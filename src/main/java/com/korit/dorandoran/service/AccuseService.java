package com.korit.dorandoran.service;

import org.springframework.http.ResponseEntity;

import com.korit.dorandoran.dto.request.accuse.PatchBlackListRequestDto;
import com.korit.dorandoran.dto.request.accuse.PostAccuseRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.accuse.GetAccuseDetailResponseDto;
import com.korit.dorandoran.dto.response.accuse.GetAccuseListResponseDto;
import com.korit.dorandoran.dto.response.accuse.GetBlackListResponseDto;

public interface AccuseService {

  ResponseEntity<ResponseDto> postAccuse(PostAccuseRequestDto dto);

  ResponseEntity<? super GetAccuseListResponseDto> getAccuseList(String userId);

  ResponseEntity<? super GetAccuseDetailResponseDto> getAccuseDetail(Integer accuseId);

  ResponseEntity<ResponseDto> approveAccuse(Integer accuseId);

  ResponseEntity<ResponseDto> rejectedAccuse(Integer accuseId);

  ResponseEntity<? super GetBlackListResponseDto> getBlackList();

  ResponseEntity<ResponseDto> cancelBlackList(PatchBlackListRequestDto dto);

  ResponseEntity<ResponseDto> setBlackList(PatchBlackListRequestDto dto);
}
