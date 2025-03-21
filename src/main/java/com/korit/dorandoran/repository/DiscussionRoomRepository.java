package com.korit.dorandoran.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korit.dorandoran.entity.DiscussionRoomEntity;
import com.korit.dorandoran.repository.resultset.GetDetailDiscussionResultSet;
import com.korit.dorandoran.repository.resultset.GetDiscussionResultSet;
import com.korit.dorandoran.repository.resultset.GetMainGenDiscListResultSet;
import com.korit.dorandoran.repository.resultset.GetMyDiscussionResultSet;

@Repository
public interface DiscussionRoomRepository extends JpaRepository<DiscussionRoomEntity, Integer> {

        boolean existsByRoomTitle(String roomTitle);

        boolean existsByRoomId(Integer roomId);

        @Query(value = "SELECT " +
        "D.room_id AS roomId, " +
        "ANY_VALUE(U.user_id) AS userId, " +
        "ANY_VALUE(U.nick_name) AS nickName, " +
        "ANY_VALUE(U.profile_image) AS profileImage, " +
        "D.room_description AS roomDescription, " +
        "D.discussion_type AS discussionType, " +
        "ANY_VALUE(D.discussion_image) AS discussionImage, " +
        "D.created_room AS createdRoom, " +
        "D.room_title AS roomTitle, " +
        "D.update_status AS updateStatus, " +
        "ANY_VALUE(P.agree_opinion) AS agreeOpinion, " +
        "ANY_VALUE(P.opposite_opinion) AS oppositeOpinion, " +
        "ANY_VALUE(P.discussion_end) AS discussionEnd, " +
        "COALESCE(commentCounts.commentCount, 0) AS commentCount, " +
        "COALESCE(likeCounts.likeCount, 0) AS likeCount, " +
        "ANY_VALUE(isLike.isLike) AS isLike "+
        "FROM discussion_room D " +
        "LEFT JOIN user U ON D.user_id = U.user_id " +
        "LEFT JOIN post_discussion P ON D.room_id = P.room_id " +
        "LEFT JOIN (SELECT room_id, COUNT(*) AS commentCount FROM comments GROUP BY room_id) AS commentCounts " +
        "ON D.room_id = commentCounts.room_id " +
        "LEFT JOIN (SELECT target_id, COUNT(DISTINCT user_id) AS likeCount FROM likes WHERE like_type = 'POST' GROUP BY target_id) AS likeCounts " +
        "ON D.room_id = likeCounts.target_id " +
        "LEFT JOIN (SELECT target_id, " +
        "                  CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS isLike " +  
        "           FROM likes " +
        "           WHERE like_type = 'POST' AND user_id = :userId "  +  
        "           GROUP BY target_id) AS isLike " +
        "ON D.room_id = isLike.target_id " +
        "GROUP BY D.room_id " +
        "ORDER BY D.created_room DESC",
        nativeQuery = true)
        List<GetDiscussionResultSet> getList(@Param("userId") String userId);

        @Query(value = "SELECT " +
                        "U.user_id, " +
                        "U.nick_name, " +
                        "U.profile_image, " +
                        "D.room_id, " +
                        "D.room_description, " +
                        "D.discussion_type, " +
                        "D.discussion_image, " +
                        "D.created_room, " +
                        "D.room_title, " +
                        "D.update_status, " +
                        "P.agree_opinion, " +
                        "P.opposite_opinion, " +
                        "P.discussion_end, " +
                        "(SELECT COUNT(*) FROM comments C WHERE C.room_id = D.room_id) AS commentCount, " +
                        "(SELECT COUNT(DISTINCT L.user_id) FROM likes L WHERE L.target_id = D.room_id AND L.like_type = 'POST') AS likeCount "
                        +
                        "FROM discussion_room D " +
                        "LEFT JOIN user U ON D.user_id = U.user_id " +
                        "LEFT JOIN post_discussion P ON D.room_id = P.room_id " +
                        "WHERE D.room_id = :roomId", nativeQuery = true)
        GetDetailDiscussionResultSet getDiscussion(@Param("roomId") Integer roomId);

        @Query(value = "SELECT " +
                        "d.user_id " +
                        "FROM discussion_room d " +
                        "WHERE d.room_id = :roomId", nativeQuery = true)
        String findUserIdByRoomId(@Param("roomId") Integer roomId);

        @Query(value = "SELECT t1.* " +
                        "FROM dorandoran.discussion_room t1 " +
                        "JOIN ( " +
                        "SELECT discussion_type, MAX(created_room) AS latest_created " +
                        "FROM dorandoran.discussion_room " +
                        "GROUP BY discussion_type " +
                        ") t2 " +
                        "ON t1.discussion_type = t2.discussion_type AND t1.created_room = t2.latest_created; ", nativeQuery = true)
        List<GetMainGenDiscListResultSet> getMainGenDiscList();

        @Query(value = "SELECT " +
                        "dr.room_id, " +
                        "dr.user_id, " +
                        "dr.room_title, " +
                        "dr.room_description, " +
                        "dr.discussion_image, " +
                        "dr.update_status, " +
                        "COALESCE(like_count, 0) AS like_count, " +
                        "COALESCE(comment_count, 0) AS comment_count, " +
                        "pd.discussion_end " +
                        "FROM discussion_room dr " +
                        "LEFT JOIN ( " +
                        "SELECT target_id, COUNT(user_id) AS like_count " +
                        "FROM `likes` " +
                        "WHERE like_type = 'POST' " +
                        "GROUP BY target_id " +
                        ") l ON dr.room_id = l.target_id " +
                        "LEFT JOIN ( " +
                        "SELECT room_id, COUNT(*) AS comment_count " +
                        "FROM comments " +
                        "GROUP BY room_id " +
                        ") c ON dr.room_id = c.room_id " +
                        "LEFT JOIN post_discussion pd ON dr.room_id = pd.room_id " +
                        "WHERE dr.user_id = :userId " +
                        "ORDER BY dr.room_id DESC;", nativeQuery = true)
        List<GetMyDiscussionResultSet> getMyDiscussionList(@Param("userId") String userId);

        DiscussionRoomEntity findByRoomId(Integer roomId);
        DiscussionRoomEntity findByRoomIdAndUserId(Integer roomId, String userId);

        @Query(value = "SELECT room_id FROM discussion_room ORDER BY room_id ASC", nativeQuery = true)
        List<Integer> getRooms();

        @Query(value = "SELECT " +
                        "U.user_id, " +
                        "U.nick_name, " +
                        "U.profile_image, " +
                        "D.room_id, " +
                        "D.room_description, " +
                        "D.discussion_type, " +
                        "D.discussion_image, " +
                        "D.created_room, " +
                        "D.room_title, " +
                        "D.update_status, " +
                        "P.agree_opinion, " +
                        "P.opposite_opinion, " +
                        "P.discussion_end, " +
                        "(SELECT COUNT(*) FROM comments C WHERE C.room_id = D.room_id) AS commentCount, " +
                        "(SELECT COUNT(DISTINCT L.user_id) FROM likes L WHERE L.target_id = D.room_id AND L.like_type = 'POST') AS likeCount "
                        +
                        "FROM discussion_room D " +
                        "LEFT JOIN user U ON D.user_id = U.user_id " +
                        "LEFT JOIN post_discussion P ON D.room_id = P.room_id " +
                        "LEFT JOIN comments C ON D.room_id = C.room_id " +
                        "LEFT JOIN likes L ON D.room_id = L.target_id AND L.like_type = 'POST' " +
                        "WHERE D.room_title LIKE CONCAT('%', :roomTitle, '%') " +
                        "GROUP BY D.room_id " +
                        "ORDER BY D.room_id DESC ", nativeQuery = true)
        List<GetDiscussionResultSet> findByRoomTitleContaining(@Param("roomTitle") String roomTitle);

}
