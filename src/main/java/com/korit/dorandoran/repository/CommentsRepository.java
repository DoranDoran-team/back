package com.korit.dorandoran.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korit.dorandoran.entity.CommentsEntity;
import com.korit.dorandoran.repository.resultset.GetCommentsResultSet;

@Repository
public interface CommentsRepository extends JpaRepository<CommentsEntity, Integer>{

@Query(value = "SELECT " +
                "C.comment_id AS commentId,"+
                "C.room_id AS roomId," +
                "C.parent_id AS parentId,"+
                "U.user_id AS userId," +
                "U.nick_name AS nickName," +
                "U.profile_image AS profileImage,"+
                "C.contents AS contents," +
                "C.created_at AS createdAt," +
                "C.depth AS depth," +
                "C.update_status AS updateStatus,"+
                "C.delete_status AS deleteStatus,"+
                "C.discussion_type AS discussionType,"+
                "COUNT(L.target_id) AS likeCount "+
                "FROM comments C " +
                "LEFT JOIN user U ON C.user_id = U.user_id " +
                "LEFT JOIN likes L ON C.comment_id = L.target_id AND L.like_type = 'COMMENT' "+
                "WHERE C.room_id = :roomId "+
                "GROUP BY C.comment_id, C.room_id, C.parent_id, U.user_id, U.nick_name, " +
                "U.profile_image, C.contents, C.created_at, C.depth, " +
                "C.update_status, C.delete_status, C.discussion_type " +
                "ORDER BY c.parent_id ASC, c.created_at DESC "
                , nativeQuery = true)
        List<GetCommentsResultSet> getComments(@Param("roomId") Integer roomId);

        Optional<CommentsEntity> findByCommentId(Integer parentId);

        CommentsEntity findByCommentIdAndRoomId(Integer commentId, Integer roomId);
        
        boolean existsByCommentId(Integer targetId);

        @Query(value="SELECT comment_id FROM comments where room_id = :roomId ORDER BY comment_id ASC", nativeQuery = true)
        List<Integer> getComment(@Param("roomId") Integer roomId);

        @Query(value= "SELECT * FROM comments WHERE room_id=:roomId", nativeQuery = true)
        List<CommentsEntity> findComments(@Param("roomId")Integer roomId);
}
