package com.korit.dorandoran.repository.resultset;

public interface GetReplyResultSet {
    Integer getRoomId();
    Integer getReplyId();
    Integer getCommentId();
    String getNickName();
    String getReplyContents();
    String getReplyTime();
    String getDiscussionType();
}
