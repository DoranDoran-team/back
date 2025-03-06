package com.korit.dorandoran.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.korit.dorandoran.dto.response.user.GetSearchUserListResponseDto;
import com.korit.dorandoran.entity.UserEntity;
import com.korit.dorandoran.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * [GET] /api/users/search?keyword=ooo
     * 
     * keyword 부분 검색
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(@RequestParam("keyword") String keyword) {
        List<UserEntity> entityList = userService.searchUsers(keyword);
        return GetSearchUserListResponseDto.success(entityList);
    }
}
