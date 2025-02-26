package com.korit.dorandoran.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.korit.dorandoran.common.object.LikeType;
import com.korit.dorandoran.dto.response.ResponseDto;
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
    // @Override
    // public ResponseEntity<ResponseDto> postLike(PostLikeRequestDto dto) {
    //     try {
    //         Integer targetId = dto.getTargetId();
    //         String userId = dto.getUserId();
    //         LikeType likeType = dto.getLikeType();

    //         boolean isExist = likesRepository.existsByTargetIdAndUserIdAndLikeType(targetId, userId, likeType);
    //         if (isExist) return ResponseDto.duplicatedLike();

    //         if (likeType == LikeType.POST){
    //             boolean isDiscussion = discussionRoomRepository.existsByRoomId(targetId);
    //             if (!isDiscussion) return ResponseDto.noExistRoom();
                
    //             LikesEntity likesEntity = new LikesEntity(dto);
    //             System.out.println("LikesEntity: " + likesEntity); 
    //             System.out.println(likeType.name());
    //             likesRepository.save(likesEntity);
    //         }
    //         else if (likeType == LikeType.COMMENT) {
    //             boolean isComment = commentsRepository.existsByCommentId(targetId);
    //             if (!isComment) return ResponseDto.noExistComment();
    
    //             LikesEntity likesEntity = new LikesEntity(dto);
    //             System.out.println("LikesEntity: " + likesEntity); 
    //             System.out.println(likeType.name());
    //             likesRepository.save(likesEntity);
    //         }

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseDto.databaseError();
    //     }
    //     return ResponseDto.success();
    // }

    @Override
    public ResponseEntity<ResponseDto> postLike(Integer targetId, String userId, String likeTypeStr) {
        try {

            LikeType likeType = LikeType.valueOf(likeTypeStr.toUpperCase());
            System.out.println(likeType);
            
            
            if (likeTypeStr == null || likeTypeStr.isEmpty()) {
                throw new IllegalArgumentException("LikeType 값이 null이거나 비어 있습니다.");
            }

            boolean isExist = likesRepository.existsByTargetIdAndUserIdAndLikeType(targetId, userId, likeType);
            if (isExist) return ResponseDto.duplicatedLike();


            if (likeType == LikeType.POST){
                boolean isDiscussion = discussionRoomRepository.existsByRoomId(targetId);
                if (!isDiscussion) return ResponseDto.noExistRoom();

                LikesEntity likesEntity = new LikesEntity(targetId, userId, likeType);
                System.out.println("LikesEntity: " + likesEntity); 
                System.out.println(likeType.name());
                likesRepository.save(likesEntity);
            }
            else if (likeType == LikeType.COMMENT) {
                boolean isComment = commentsRepository.existsByCommentId(targetId);
                if (!isComment) return ResponseDto.noExistComment();
    
                LikesEntity likesEntity = new LikesEntity(targetId, userId, likeType);
                System.out.println("LikesEntity: " + likesEntity); 
                System.out.println(likeType.name());
                likesRepository.save(likesEntity);
            }
            

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
        
    }
    

}
