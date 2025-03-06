package com.korit.dorandoran.dto.response.auth;

import java.util.List;

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
    private List<Subscriber> subscribers;

    public GetSignInResponseDto(UserEntity userEntity, List<Subscriber> subscribers) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = userEntity.getUserId();
        this.profileImage = userEntity.getProfileImage();
        this.name = userEntity.getName();
        this.telNumber = userEntity.getTelNumber();
        this.nickName = userEntity.getNickName();
        this.role = userEntity.getRole();
        this.mileage = userEntity.getMileage();
        this.statusMessage = userEntity.getStatusMessage();
        this.subscribers = subscribers;
    }

    public static ResponseEntity<GetSignInResponseDto> success(
        UserEntity userEntity,
        List<Subscriber> subscribers
    ) {
        GetSignInResponseDto responseBody = new GetSignInResponseDto(userEntity, subscribers);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
