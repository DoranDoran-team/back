package com.korit.dorandoran.repository.resultset;

public interface GetReplyResultSet {
    Integer getCommentId();
    Integer getRoomId();
    Integer getParentId();
    String getUserId();
    String getNickName();
    String getProfileImage();
    String getReplyContents();
    String getReplyTime();
    String getDiscussionType();
    Integer getUpdateStatus();
    Integer getDepth();
    
}
