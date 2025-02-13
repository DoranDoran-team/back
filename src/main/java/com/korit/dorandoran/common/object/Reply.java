package com.korit.dorandoran.common.object;

import java.util.ArrayList;
import java.util.List;

import com.korit.dorandoran.repository.resultset.GetCommentResultSet;
import com.korit.dorandoran.repository.resultset.GetReplyResultSet;

import lombok.Getter;

@Getter
public class Reply {
    
    private Integer commentId;
    private Integer roomId;
    private Integer parentId;
    private String userId;
    private String nickName;
    private String profileImage;
    private String replyContents;
    private String replyTime;
    private String discussionType;
    private boolean updateStatus;
    private Integer depth;

    public Reply(GetReplyResultSet resultSet){
        this.commentId = resultSet.getCommentId();
        this.roomId = resultSet.getRoomId();
        this.userId = resultSet.getUserId();
        this.nickName = resultSet.getNickName();
        this.profileImage = resultSet.getProfileImage();
        this.replyContents = resultSet.getReplyContents();
        this.replyTime = resultSet.getReplyTime();
        this.discussionType = resultSet.getDiscussionType();
        this.updateStatus = resultSet.getUpdateStatus() !=null && resultSet.getUpdateStatus()== 0 ? false : true;
        this.depth = resultSet.getDepth();
    }
    
    public static List<Reply> getList(List<GetReplyResultSet> resultSets) {
        List<Reply> replies = new ArrayList<>();
        for(GetReplyResultSet resultSet : resultSets){
            replies.add(new Reply(resultSet));
        }
        return replies;
    }
}
