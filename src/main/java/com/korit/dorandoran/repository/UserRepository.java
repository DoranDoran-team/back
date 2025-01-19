package com.korit.dorandoran.repository;

import com.korit.dorandoran.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{

    // 회원가입 시, 중복 아이디 검사
    boolean existsByUserId(String userId);

    // 중복 전화번호 검사
    boolean existsByTelNumber(String telNumber);

    // 로그인
    UserEntity findByUserId(String userId);

    // 로그인을 위한 snsId & 가입 경로 찾기
	UserEntity findBySnsIdAndJoinPath(String snsId, String joinPath);

    // 아이디 찾기 - 이름 & 전화번호
    boolean existsByNameAndTelNumber(String name, String telNumber);
}
