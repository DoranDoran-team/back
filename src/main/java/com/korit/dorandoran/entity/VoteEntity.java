package com.korit.dorandoran.entity;

import com.korit.dorandoran.common.util.TodayCreator;
import com.korit.dorandoran.dto.request.vote.PostVoteRequestDto;
import com.korit.dorandoran.entity.pk.VotePK;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="vote")
@Entity(name="vote")
@IdClass(VotePK.class)
public class VoteEntity {
    
    @Id
    private Integer roomId;
    
    @Id
    private String userId;

    @Id
    private String voteChoice;

    private String createdAt;

    public VoteEntity(PostVoteRequestDto dto, Integer roomId, String userId ){
        
        String createdAt = TodayCreator.todayCreator();
        
        this.roomId = roomId;
        this.userId = userId;
        this.voteChoice = dto.getVoteChoice();
        this.createdAt = createdAt;
    }

}
