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
    private String roomId;
    
    @Id
    private String userId;

    @Id
    private String voteChoice;

    private String createdAt;

    // DiscussionRoom의 정수형 room_id를 사용하는 경우: toString()으로 변환해서 저장
    public VoteEntity(PostVoteRequestDto dto, Integer intRoomId, String userId) {
        this.roomId = String.valueOf(intRoomId);
        this.userId = userId;
        this.voteChoice = dto.getVoteChoice();
        this.createdAt = TodayCreator.todayCreator();
    }

    // 실시간 채팅방에서 생성된 문자열 id를 바로 사용하는 경우
    public VoteEntity(PostVoteRequestDto dto, String chatRoomId, String userId, boolean isChatRoom) {
        // isChatRoom이 true이면 chatRoomId(문자열)를 그대로 사용
        this.roomId = chatRoomId;
        this.userId = userId;
        this.voteChoice = dto.getVoteChoice();
        this.createdAt = TodayCreator.todayCreator();
    }

}
