package com.korit.dorandoran.service.implement;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.korit.dorandoran.common.object.LikeType;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.like.GetLikeListResponseDto;
import com.korit.dorandoran.entity.LikesEntity;
import com.korit.dorandoran.repository.CommentsRepository;
import com.korit.dorandoran.repository.DiscussionRoomRepository;
import com.korit.dorandoran.repository.LikesRepository;
import com.korit.dorandoran.service.LikesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikesServiceImplement implements LikesService {
    
    
    private final LikesRepository likesRepository;
    private final DiscussionRoomRepository discussionRoomRepository;
    private final CommentsRepository commentsRepository;
    

    @Override
    public ResponseEntity<ResponseDto> postLike(Integer targetId, String userId, String likeTypeStr) {
        try {

            LikeType likeType = LikeType.valueOf(likeTypeStr.toUpperCase());
            
            boolean isExist = likesRepository.existsByTargetIdAndUserIdAndLikeType(targetId, userId, likeType);
            if (isExist) return ResponseDto.duplicatedLike();

            if (likeType == LikeType.POST){
                boolean isDiscussion = discussionRoomRepository.existsByRoomId(targetId);
                if (!isDiscussion) return ResponseDto.noExistRoom();

                
                LikesEntity likesEntity = new LikesEntity(targetId, userId, likeType);
                likesRepository.save(likesEntity);
            }
            else if (likeType == LikeType.COMMENT) {
                boolean isComment = commentsRepository.existsByCommentId(targetId);
                if (!isComment) return ResponseDto.noExistComment();

                
                LikesEntity likesEntity = new LikesEntity(targetId, userId, likeType);
                likesRepository.save(likesEntity);
            }
            

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
        
    }

    @Override
    public ResponseEntity<ResponseDto> deleteLike(Integer targetId, String userId, String likeTypeStr) {
        try {
            
            LikeType likeType = LikeType.valueOf(likeTypeStr.toUpperCase());

            if(likeType == LikeType.POST || likeType == LikeType.COMMENT){
                LikesEntity likesEntity = likesRepository.findByTargetIdAndLikeType(targetId, likeType);
                if (likesEntity == null) return ResponseDto.noExistedTarget();
                boolean isMatched = likesEntity.getUserId().equals(userId);
                if(!isMatched) return ResponseDto.noPermission();

                likesRepository.delete(likesEntity);
            };

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetLikeListResponseDto> getLikeList(Integer roomId, String userId) {

        
        List<Map<String,Object>> commentLikeList = new ArrayList<>();
        boolean isLikePost = false;
        try {
            boolean isDiscussion = discussionRoomRepository.existsByRoomId(roomId);
            if (!isDiscussion) return ResponseDto.noExistRoom();

            isLikePost = likesRepository.existsByTargetIdAndUserIdAndLikeType(roomId, userId, LikeType.POST);
            if (!isLikePost) isLikePost = false;
            
            List<Integer> comments = commentsRepository.getComment(roomId);
            commentLikeList = comments.stream()
                .map(comment -> {
                    Map<String, Object> commentInfo = new HashMap<>();
                    commentInfo.put("commentId", comment);
                    commentInfo.put("isCommentLike", likesRepository.existsByTargetIdAndUserIdAndLikeType(comment, userId, LikeType.COMMENT));
                    return commentInfo;
                })
                .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }return GetLikeListResponseDto.success(roomId, isLikePost, commentLikeList);
    }
    

}
