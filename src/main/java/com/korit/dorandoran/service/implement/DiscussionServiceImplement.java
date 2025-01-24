package com.korit.dorandoran.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.korit.dorandoran.dto.request.postDiscussion.PostDiscussionWriteRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.entity.DiscussionRoomEntity;
import com.korit.dorandoran.entity.PostDiscussionEntity;
import com.korit.dorandoran.repository.DiscussionRoomRepository;
import com.korit.dorandoran.repository.PostDiscussionRepository;
import com.korit.dorandoran.repository.UserRepository;
import com.korit.dorandoran.service.DiscussionService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class DiscussionServiceImplement implements DiscussionService {

    private final DiscussionRoomRepository discussionRoomRepository;
    private final PostDiscussionRepository postDiscussionRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseDto> postDiscussionWite(PostDiscussionWriteRequestDto dto) {
        String userId = dto.getUserId();
        
        try {
            boolean isMatchedUser = userRepository.existsByUserId(userId);
            if (!isMatchedUser) return ResponseDto.noExistUserId();
            
            String roomTitle = dto.getRoomTitle();
            boolean isMatchedTitle = discussionRoomRepository.existsByRoomTitle(roomTitle);
            if (!isMatchedTitle) return ResponseDto.duplicatedRoomTitle();

            
            DiscussionRoomEntity discussionRoomEntity = new DiscussionRoomEntity(dto);
            PostDiscussionEntity postDiscussionEntity = new PostDiscussionEntity(dto);
            discussionRoomRepository.save(discussionRoomEntity);
            postDiscussionRepository.save(postDiscussionEntity);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }
    
}
