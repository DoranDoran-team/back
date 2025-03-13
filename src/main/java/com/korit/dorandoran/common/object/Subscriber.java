package com.korit.dorandoran.common.object;

import java.util.ArrayList;
import java.util.List;

import com.korit.dorandoran.entity.UserEntity;

import lombok.Getter;

@Getter
public class Subscriber {
    private String nickName;
    private String profileImage;
    private String userId;

    public Subscriber(UserEntity userEntity) {
        this.nickName = userEntity.getNickName();
        this.profileImage = userEntity.getProfileImage();
        this.userId = userEntity.getUserId();
    }

    public static List<Subscriber> getList(List<UserEntity> userEntities) {
        List<Subscriber> subscribers = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            Subscriber subscriber = new Subscriber(userEntity);
            subscribers.add(subscriber);
        }
        return subscribers;
    }
}
