package com.korit.dorandoran.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korit.dorandoran.entity.CommentEntity;
import com.korit.dorandoran.repository.resultset.GetCommentResultSet;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    CommentEntity findByCommentIdAndRoomId(Integer commentId, Integer roomId);

    @Query(value = "SELECT " +
            "C.comment_id AS commentId," +
            "D.room_id AS roomId," +
            "U.nick_name AS nickName," +
            "U.profile_image AS profileImage," +
            "C.contents AS commentContents," +
            "C.created_at AS commentTime," +
            "C.discussion_type AS discussionType " +
            "FROM comments C " +
            "LEFT JOIN discussion_room D ON C.room_id = D.room_id " +
            "LEFT JOIN user U ON C.user_id = U.user_id " +
            "WHERE C.room_id = :roomId", nativeQuery = true)
    List<GetCommentResultSet> getComments(@Param("roomId") Integer roomId);

}
