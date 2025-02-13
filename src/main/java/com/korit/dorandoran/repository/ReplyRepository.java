package com.korit.dorandoran.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korit.dorandoran.entity.ReplyEntity;
import com.korit.dorandoran.repository.resultset.GetReplyResultSet;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Integer>{

    boolean existsByCommentId(Integer commentId);

    @Query(value = 
        "SELECT "+
        "D.room_id AS roomId,"+
        "R.reply_id AS replyId,"+
        "C.comment_id AS commentId," +
        "U.user_id AS userId,"+
        "U.nick_name AS nickName,"+
        "U.profile_image AS profileImage,"+
        "R.reply_contents AS replyContents,"+
        "R.reply_time AS replyTime,"+
        "R.discussion_type AS discussionType "+
        "FROM reply R " +
        "LEFT JOIN discussion_room D ON R.room_id = D.room_id " +
        "LEFT JOIN comment C ON R.comment_id = C.comment_id " +
        "LEFT JOIN user U ON R.user_id = U.user_id " +
        "WHERE R.comment_id = :roomId ",
        nativeQuery = true)
    List<GetReplyResultSet> getReplies(@Param("roomId") Integer roomId);
}
