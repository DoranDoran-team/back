package com.korit.dorandoran.common.object;

import java.util.ArrayList;
import java.util.List;

import com.korit.dorandoran.repository.resultset.GetReplyResultSet;

import lombok.Getter;

@Getter
public class Reply {
    
    private Integer replyId;
    private Integer commentId;
    private String nickName;
    private String replyContents;
    private String replyTime;
    private String discussionType;
    private Integer roomdId;

    public Reply(GetReplyResultSet resultSet){
        this.replyId = resultSet.getReplyId();
        this.commentId = resultSet.getCommentId();
        this.nickName = resultSet.getNickName();
        this.replyContents = resultSet.getReplyContents();
        this.replyTime = resultSet.getReplyTime();
        this.discussionType = resultSet.getDiscussionType();
        this.roomdId = resultSet.getRoomId();
    }
    
    public static List<Reply> getList(List<GetReplyResultSet> resultSets) {
        List<Reply> replies = new ArrayList<>();
        for(GetReplyResultSet resultSet : resultSets){
            replies.add(new Reply(resultSet));
        }
        return replies;
    }
}
