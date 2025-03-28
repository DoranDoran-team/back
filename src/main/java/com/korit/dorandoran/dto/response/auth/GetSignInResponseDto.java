package com.korit.dorandoran.dto.response.auth;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.korit.dorandoran.common.object.Subscriber;
import com.korit.dorandoran.dto.response.ResponseCode;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.ResponseMessage;
import com.korit.dorandoran.entity.UserEntity;

import lombok.Getter;

@Getter
public class GetSignInResponseDto extends ResponseDto{
    
    private String userId;
    private String profileImage;
    private String name;
    private String telNumber;
    private String nickName;
    private Boolean role;
    private Integer mileage;
    private String statusMessage;
    private List<Map<String, Object>> isVoted;
    private List<Subscriber> subscribers; // 내가 구독한 사람 리스트
    private Integer subscribersCount;

    private Integer accuseCount;
    private Boolean accuseState;
    private String accuseTime;

    public GetSignInResponseDto(UserEntity userEntity, List<Map<String, Object>> isVoted, 
    List<Subscriber> subscribers, Integer subscribersCount) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = userEntity.getUserId();
        this.profileImage = userEntity.getProfileImage();
        this.name = userEntity.getName();
        this.telNumber = userEntity.getTelNumber();
        this.nickName = userEntity.getNickName();
        this.role = userEntity.getRole();
        this.mileage = userEntity.getMileage();
        this.statusMessage = userEntity.getStatusMessage();
        this.isVoted = isVoted;
        this.accuseCount = userEntity.getAccuseCount();
        this.accuseState = userEntity.getAccuseState();
        this.accuseTime = userEntity.getAccuseTime();

        this.subscribers = subscribers;
        this.subscribersCount = subscribersCount;
    }

    public static ResponseEntity<GetSignInResponseDto> success(
        UserEntity userEntity, 
        List<Map<String, Object>> isVoted,
        List<Subscriber> subscribers,
        Integer subscribersCount
    ) {
        GetSignInResponseDto responseBody = new GetSignInResponseDto(userEntity, isVoted, 
        subscribers, subscribersCount);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
