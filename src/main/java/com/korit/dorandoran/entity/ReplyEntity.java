package com.korit.dorandoran.entity;

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
@Entity(name="Reply")
@Table(name="reply")
public class ReplyEntity {
    
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Integer replyId;
    private Integer commentId;
    private String userId;
    private Integer replyParentId;
    private String replyContents;
    private String replyTime;
    private String discussionType;
    private Integer roomId;
    private Integer depth;
    private boolean updateStatus;
}

