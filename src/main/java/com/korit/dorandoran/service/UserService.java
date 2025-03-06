package com.korit.dorandoran.service;

import java.util.List;
import com.korit.dorandoran.entity.UserEntity;

public interface UserService {

    List<UserEntity> searchUsers(String keyword);
}
