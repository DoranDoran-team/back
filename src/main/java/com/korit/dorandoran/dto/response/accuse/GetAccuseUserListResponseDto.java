package com.korit.dorandoran.dto.response.accuse;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.korit.dorandoran.common.object.AccuseUserList;
import com.korit.dorandoran.dto.response.ResponseCode;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.ResponseMessage;
import com.korit.dorandoran.repository.resultset.GetAccuseUserListResultSet;

import lombok.Getter;

@Getter
public class GetAccuseUserListResponseDto extends ResponseDto {

  List<AccuseUserList> accuseUserList;

  public GetAccuseUserListResponseDto(List<GetAccuseUserListResultSet> accuseUserListResultSet) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.accuseUserList = AccuseUserList.getUserList(accuseUserListResultSet);
  }

  public static ResponseEntity<GetAccuseUserListResponseDto> success(
      List<GetAccuseUserListResultSet> accuseUserListResultSet) {
    GetAccuseUserListResponseDto responseBody = new GetAccuseUserListResponseDto(accuseUserListResultSet);
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

}
