package com.korit.dorandoran.entity;

import com.korit.dorandoran.common.util.TodayCreator;
import com.korit.dorandoran.dto.request.comment.PatchCommentRequestDto;
import com.korit.dorandoran.dto.request.comment.PostCommentRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Comments")
@Table(name = "Comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    private Integer roomId;
    private String userId;
    private String commentContents;
    private String commentTime;
    private String discussionType;
    private boolean updateStatus;

    public CommentEntity(PostCommentRequestDto dto, Integer roomId) {

        String commentTime = TodayCreator.todayCreator();

        this.roomId = roomId;
        this.userId = dto.getUserId();
        this.commentContents = dto.getCommentContents();
        this.commentTime = commentTime;
        this.discussionType = dto.getDiscussionType();
        this.updateStatus = false;
    }

    public void patch(PatchCommentRequestDto dto) {
        this.commentContents = dto.getCommentContents();
    }
}
