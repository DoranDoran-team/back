package com.korit.dorandoran.repository.resultset;

public interface GetMyDiscussionResultSet {
    Integer getRoomId();

    String getDiscussionImage();

    String getCreatedRoom();

    String getRoomTitle();

    Integer getUpdateStatus();

    Integer getCommentCount();

    Integer getLikesCount();

    String getRoomDescription();

    String getDiscussionEnd();
}
