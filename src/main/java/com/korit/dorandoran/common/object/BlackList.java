package com.korit.dorandoran.common.object;

import java.util.ArrayList;
import java.util.List;

import com.korit.dorandoran.entity.UserEntity;

import lombok.Getter;

@Getter
public class BlackList {
    
    private String userId;
    private String nickName;
    private String profileImage;
    private Integer accuseCount;
    private String accuseTime;
    private Boolean accuseState;

    public BlackList(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.nickName = userEntity.getNickName();
        this.profileImage = userEntity.getProfileImage();
        this.accuseCount = userEntity.getAccuseCount();
        this.accuseTime = userEntity.getAccuseTime();
        this.accuseState = userEntity.getAccuseState();
    }

    public static List<BlackList> getList(List<UserEntity> userEntities) {
        List<BlackList> blackLists = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            BlackList blackList = new BlackList(userEntity);
            blackLists.add(blackList);
        }
        return blackLists;
    }
}
