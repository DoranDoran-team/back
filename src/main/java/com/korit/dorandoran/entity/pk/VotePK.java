package com.korit.dorandoran.entity.pk;

import java.io.Serializable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotePK implements Serializable{
    
    @Column(name="room_id")
    private Integer roomId;
    @Column(name="user_id")
    private String userId;
    @Column(name="vote_choice")
    private String voteChoice;
}
