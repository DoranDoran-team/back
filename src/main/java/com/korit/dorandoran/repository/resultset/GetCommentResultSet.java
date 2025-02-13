package com.korit.dorandoran.repository.resultset;


public interface GetCommentResultSet {
    Integer getCommentId();
    Integer getRoomId();
    String getUserId();
    String getNickName();
    String getProfileImage();
    String getCommentContents();
    String getCommentTime();
    String getDiscussionType();
    Integer getUpdateStatus();

}
