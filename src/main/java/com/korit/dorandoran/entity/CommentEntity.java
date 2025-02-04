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
@Entity(name="Comemnt")
@Table(name="comment")
public class CommentEntity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer commentId;
    private Integer roomId;
    private String userId;
    private String comemntContents;
    private String commentTime;
    private String discussionType;
}
