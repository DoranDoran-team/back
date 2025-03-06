package com.korit.dorandoran.dto.response.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.korit.dorandoran.dto.response.ResponseCode;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.ResponseMessage;
import com.korit.dorandoran.entity.UserEntity;

import lombok.Getter;

@Getter
public class GetSearchUserListResponseDto extends ResponseDto {

    private List<SearchUserData> userList;

    // private 생성자: Entity 리스트 -> SearchUserData 리스트로 변환
    private GetSearchUserListResponseDto(List<UserEntity> entities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userList = entities.stream()
            .map(SearchUserData::new)  // (UserEntity -> SearchUserData)
            .collect(Collectors.toList());
    }

    /**
     * - 성공 시 ResponseEntity 생성 
     * - 기존처럼 static 메서드로 응답 만들어 반환
     */
    public static ResponseEntity<GetSearchUserListResponseDto> success(List<UserEntity> entities) {
        GetSearchUserListResponseDto responseBody = new GetSearchUserListResponseDto(entities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
