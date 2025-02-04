package com.korit.dorandoran.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.korit.dorandoran.common.object.Comment;
import com.korit.dorandoran.dto.request.postDiscussion.PostDiscussionWriteRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.discussion.GetDiscussionListResponseDto;
import com.korit.dorandoran.dto.response.discussion.GetDiscussionResponseDto;
import com.korit.dorandoran.dto.response.discussion.GetSignInUserResponseDto;
import com.korit.dorandoran.entity.DiscussionRoomEntity;
import com.korit.dorandoran.entity.PostDiscussionEntity;
import com.korit.dorandoran.entity.UserEntity;
import com.korit.dorandoran.repository.CommentRepository;
import com.korit.dorandoran.repository.DiscussionRoomRepository;
import com.korit.dorandoran.repository.PostDiscussionRepository;
import com.korit.dorandoran.repository.ReplyRepository;
import com.korit.dorandoran.repository.UserRepository;
import com.korit.dorandoran.repository.resultset.GetCommentResultSet;
import com.korit.dorandoran.repository.resultset.GetDiscussionResultSet;
import com.korit.dorandoran.repository.resultset.GetReplyResultSet;
import com.korit.dorandoran.service.DiscussionService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class DiscussionServiceImplement implements DiscussionService {

    private final DiscussionRoomRepository discussionRoomRepository;
    private final PostDiscussionRepository postDiscussionRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    @Override
    public ResponseEntity<ResponseDto> postDiscussionWite(PostDiscussionWriteRequestDto dto) {
        
        try {
            DiscussionRoomEntity discussionRoomEntity = new DiscussionRoomEntity(dto);
            discussionRoomRepository.save(discussionRoomEntity);
            discussionRoomRepository.flush();

            Integer roomId = discussionRoomEntity.getRoomId();
            if (roomId == null) {
                throw new RuntimeException("roomId가 NULL입니다.");
            }
            
            PostDiscussionEntity postDiscussionEntity = new PostDiscussionEntity(dto, roomId);
            postDiscussionRepository.save(postDiscussionEntity);
            
        } catch (Exception e) {
            throw new RuntimeException("트랜잭션 롤백 문제 발생", e);
        }
        return ResponseDto.success();
    
}

    @Override
    public ResponseEntity<? super GetDiscussionListResponseDto> getDiscussionList() {
        List<GetDiscussionResultSet> resultSet = new ArrayList<>();
        
        try {
            
            resultSet = discussionRoomRepository.getList();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetDiscussionListResponseDto.success(resultSet);
    }

    @Override
    public ResponseEntity<? super GetDiscussionResponseDto> getDiscussion(Integer roomId) {
        GetDiscussionResultSet discussionResultSet;
        List<Comment> comments = new ArrayList<>();
        try {

            boolean isExisted = discussionRoomRepository.existsByRoomId(roomId);
            if (!isExisted) return ResponseDto.noExistRoom();

            discussionResultSet = discussionRoomRepository.getDiscussion(roomId);
            if (discussionResultSet == null) return ResponseDto.noExistRoom();
            
            
        List<GetCommentResultSet> commentResultSets = commentRepository.getComments(roomId);
        if (commentResultSets == null || commentResultSets.isEmpty()) {
            return GetDiscussionResponseDto.success(discussionResultSet, new ArrayList<>()); 
        }

        for (GetCommentResultSet commentResultSet : commentResultSets) {
            List<GetReplyResultSet> replyResultSets = replyRepository.getReplies(commentResultSet.getCommentId());
            comments.add(new Comment(commentResultSet, replyResultSets));
        }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetDiscussionResponseDto.success(discussionResultSet, comments);
    }

    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignIn(String userId) {
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return ResponseDto.noExistUserId();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetSignInUserResponseDto.success(userEntity);
    }

    
}
