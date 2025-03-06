package com.korit.dorandoran.repository;

import com.korit.dorandoran.entity.UserEntity;
import com.korit.dorandoran.repository.resultset.GetAccuseUserListResultSet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

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

    // 아이디 찾기 - 전화번호를 통해 아이디 반환
    UserEntity findByTelNumber(String telNumber);

    // 비밀번호 찾기 - 아이디 & 전화번호
    boolean existsByUserIdAndTelNumber(String userId, String telNumber);

    UserEntity findByUserIdAndTelNumber(String userId, String telNumber);

    @Query(value = "SELECT u.user_id AS userId, u.name AS name, u.profile_image AS profileImage, " +
            "COUNT(a.accuse_id) AS accuseCount " +
            "FROM user u " +
            "LEFT JOIN accuse a ON u.user_id = a.user_id " +
            "WHERE LOWER(u.user_id) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "GROUP BY u.user_id, u.name, u.profile_image", nativeQuery = true)
    List<GetAccuseUserListResultSet> searchByNameOrUserId(@Param("keyword") String keyword);

    List<GetAccuseUserListResultSet> findByUserIdContaining(String userId);

    List<UserEntity> findByRole(Boolean role);

    // List<UserEntity> findByUserIdContainingIgnoreCase(String keyword);
    
    List<UserEntity> findByUserIdContainingIgnoreCaseOrNickNameContainingIgnoreCase(String userIdKeyword, String nickNameKeyword);

    // 추가: 닉네임으로 검색 (정확한 매칭)
    UserEntity findByNickName(String nickName);
}
