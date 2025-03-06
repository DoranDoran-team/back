package com.korit.dorandoran.service.implement;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.korit.dorandoran.entity.UserEntity;
import com.korit.dorandoran.repository.UserRepository;
import com.korit.dorandoran.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserEntity> searchUsers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList();
        }
        // 부분 검색
        return userRepository.findByUserIdContainingIgnoreCaseOrNickNameContainingIgnoreCase(keyword, keyword);
    }
}
