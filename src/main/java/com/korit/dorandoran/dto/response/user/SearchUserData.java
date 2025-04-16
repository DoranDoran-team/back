package com.korit.dorandoran.dto.response.user;

import com.korit.dorandoran.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserData {

    private String userId;
    private String nickName;
    private String profileImage;
    private String statusMessage;
    private Boolean role;

    public SearchUserData(UserEntity entity) {
        this.userId = entity.getUserId();
        this.nickName = entity.getNickName();
        this.profileImage = entity.getProfileImage();
        this.statusMessage = entity.getStatusMessage();
        this.role = entity.getRole();
    }
}
