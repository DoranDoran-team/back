package com.korit.dorandoran.repository.resultset;


public interface GetCommentResultSet {
    Integer getCommentId();
    Integer getRoomId();
    String getNickName();
    String getProfileImage();
    String getCommentContents();
    String getCommentTime();
    String getDiscussionType();
}
