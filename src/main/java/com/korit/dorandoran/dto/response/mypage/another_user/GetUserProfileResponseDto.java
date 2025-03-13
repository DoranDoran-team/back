package com.korit.dorandoran.dto.response.mypage.another_user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.korit.dorandoran.common.object.MyDiscussion;
import com.korit.dorandoran.dto.response.ResponseCode;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.ResponseMessage;
import com.korit.dorandoran.entity.UserEntity;
import com.korit.dorandoran.repository.resultset.GetMyDiscussionResultSet;

import lombok.Getter;

@Getter
public class GetUserProfileResponseDto extends ResponseDto{
    
    private String profileImage;
    private String nickName;
    private String statusMessage;
    List<MyDiscussion> myDiscussions;
    private Integer subscribers;

    public GetUserProfileResponseDto(
        UserEntity userEntity, 
        List<GetMyDiscussionResultSet> resultSet,
        Integer subscribers
    ) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.profileImage = userEntity.getProfileImage();
        this.nickName = userEntity.getNickName();
        this.statusMessage = userEntity.getStatusMessage();
        this.myDiscussions = MyDiscussion.getList(resultSet);
        this.subscribers = subscribers;
    }

    public static ResponseEntity<GetUserProfileResponseDto> success(
        UserEntity userEntity,
        List<GetMyDiscussionResultSet> resultSet,
        Integer subscribers
        ) {
            GetUserProfileResponseDto responseBody = new GetUserProfileResponseDto(userEntity, resultSet, subscribers);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
