package com.korit.dorandoran.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.korit.dorandoran.dto.request.vote.PostVoteRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.vote.GetVoteResultResponseDto;
import com.korit.dorandoran.entity.VoteEntity;
import com.korit.dorandoran.repository.DiscussionRoomRepository;
import com.korit.dorandoran.repository.VoteRepository;
import com.korit.dorandoran.service.VoteService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class VoteServiceImplement implements VoteService{
    
    private final DiscussionRoomRepository discussionRoomRepository;
    private final VoteRepository voteRepository;


    @Override
    public ResponseEntity<ResponseDto> postVote(PostVoteRequestDto dto, String userId, Integer roomId) {
        
        try {

            boolean isRoom = discussionRoomRepository.existsByRoomId(roomId);
            if (!isRoom) return ResponseDto.noExistRoom();

            boolean isVote = voteRepository.existsByRoomIdAndUserId(roomId, userId);
            if(isVote) return ResponseDto.duplicatedVote();

            VoteEntity voteEntity = new VoteEntity(dto, roomId, userId);
            voteRepository.save(voteEntity);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }


    @Override
    public ResponseEntity<? super GetVoteResultResponseDto> getVoteResult(Integer roomId) {
        List<VoteEntity> voteResult = new ArrayList<>();
        try {

            boolean isRoom = discussionRoomRepository.existsByRoomId(roomId);
            if (!isRoom) return ResponseDto.noExistRoom();

            voteResult = voteRepository.getResult(roomId);
            if(voteResult == null) {
                voteResult = new ArrayList<>();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetVoteResultResponseDto.success(voteResult);
    }
}
