package com.korit.dorandoran.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.korit.dorandoran.entity.DiscussionRoomEntity;
import com.korit.dorandoran.repository.resultset.GetDetailDiscussionResultSet;
import com.korit.dorandoran.repository.resultset.GetDiscussionResultSet;

@Repository
public interface DiscussionRoomRepository extends JpaRepository<DiscussionRoomEntity, Integer> {

    boolean existsByRoomTitle(String roomTitle);
    boolean existsByRoomId(Integer roomId);

    @Query(value = 
    "SELECT "+
    "U.nick_name,"+
    "U.profile_image,"+
    "D.room_id,"+
    "D.discussion_type,"+
    "D.discussion_image,"+
    "D.created_room,"+
    "D.room_title,"+
    "D.update_status,"+
    "P.agree_opinion,"+
    "P.opposite_opinion,"+
    "P.discussion_end,"+
    "COUNT(C.room_id) as commentCount,"+
    "COUNT(distinct L.room_id) as likeCount " +
    "FROM discussion_room D " +
    "LEFT JOIN user U ON D.user_id = U.user_id " +
    "LEFT JOIN post_discussion P ON D.room_id = P.room_id " +
    "LEFT JOIN comment C ON D.room_id = C.room_id " +
    "LEFT JOIN dorandoran.like L ON D.room_id = L.room_id " +
    "GROUP BY D.room_id ",
    nativeQuery = true 
    )
    List<GetDiscussionResultSet> getList();

    @Query(value = "SELECT " +
    "U.user_id," +
    "U.nick_name," +
    "U.profile_image," +
    "D.room_id," +
    "D.room_description," +
    "D.discussion_type," +
    "D.discussion_image," +
    "D.created_room," +
    "D.room_title," +
    "D.update_status," +
    "P.agree_opinion," +
    "P.opposite_opinion," +
    "P.discussion_end," +
    "COUNT(C.room_id) as commentCount," +
    "COUNT(distinct L.room_id) as likeCount " +
    "FROM discussion_room D " +
    "LEFT JOIN user U ON D.user_id = U.user_id " +
    "LEFT JOIN post_discussion P ON D.room_id = P.room_id " +
    "LEFT JOIN comment C ON D.room_id = C.room_id " +
    "LEFT JOIN dorandoran.like L ON D.room_id = L.room_id " +
    "WHERE D.room_id = :roomId " +
    "GROUP BY D.room_id ", nativeQuery = true)
    GetDetailDiscussionResultSet getDiscussion(@Param("roomId") Integer roomId);

}

