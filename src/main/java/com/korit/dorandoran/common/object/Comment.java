package com.korit.dorandoran.common.object;

import java.util.ArrayList;
import java.util.List;

import com.korit.dorandoran.repository.resultset.GetCommentResultSet;
import com.korit.dorandoran.repository.resultset.GetReplyResultSet;

import lombok.Getter;

@Getter
public class Comment {
    
    private Integer commentId;
    private Integer roomId;
    private String userId;
    private String nickName;
    private String profileImage;
    private String commentContents;
    private String commentTime;
    private String discussionType;
    private boolean updateStatus;
    private List<Reply> replies;

    public Comment (GetCommentResultSet commentResultSets, List<GetReplyResultSet> replyResultSets){

        this.commentId = commentResultSets.getCommentId();
        this.roomId = commentResultSets.getRoomId();
        this.userId = commentResultSets.getUserId();
        this.nickName = commentResultSets.getNickName();
        this.profileImage = commentResultSets.getProfileImage();
        this.commentContents = commentResultSets.getCommentContents();
        this.commentTime = commentResultSets.getCommentTime();
        this.discussionType = commentResultSets.getDiscussionType();
        this.updateStatus = commentResultSets.getUpdateStatus() != null && commentResultSets.getUpdateStatus() == 0 ? false : true;
        this.replies = Reply.getList(replyResultSets);
    }
    public static List<Comment> getCommentList(List<GetCommentResultSet> resultSets, List<GetReplyResultSet> replyResultSets) {
        List<Comment> comments = new ArrayList<>();
        for(GetCommentResultSet resultSet : resultSets){
            comments.add(new Comment(resultSet, replyResultSets));
        }
        return comments;
    }
}
