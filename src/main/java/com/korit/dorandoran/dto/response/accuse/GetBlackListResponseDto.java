package com.korit.dorandoran.dto.response.accuse;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.korit.dorandoran.common.object.BlackList;
import com.korit.dorandoran.dto.response.ResponseCode;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.ResponseMessage;
import com.korit.dorandoran.entity.UserEntity;

import lombok.Getter;

@Getter
public class GetBlackListResponseDto extends ResponseDto{
    List<BlackList> blackLists; 

    public GetBlackListResponseDto(List<UserEntity> userEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.blackLists = BlackList.getList(userEntities);
    }

  public static ResponseEntity<GetBlackListResponseDto> success(
      List<UserEntity> userEntities) {
        GetBlackListResponseDto responseBody = new GetBlackListResponseDto(userEntities);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }
}
