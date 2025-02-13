package com.korit.dorandoran.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.korit.dorandoran.dto.request.comment.PatchCommentRequestDto;
import com.korit.dorandoran.dto.request.comment.PostCommentRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.entity.CommentEntity;
import com.korit.dorandoran.repository.CommentRepository;
import com.korit.dorandoran.repository.DiscussionRoomRepository;
import com.korit.dorandoran.repository.ReplyRepository;

import com.korit.dorandoran.service.CommentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImplement implements CommentService {

  private final CommentRepository commentRepository;
  private final DiscussionRoomRepository discussionRoomRepository;
  private final ReplyRepository replyRepository;

  @Override
  public ResponseEntity<ResponseDto> postComment(PostCommentRequestDto dto, Integer roomId) {

    try {

      boolean isExisted = discussionRoomRepository.existsByRoomId(roomId);
      if (!isExisted)
        return ResponseDto.noExistRoom();

      CommentEntity commentEntity = new CommentEntity(dto, roomId);
      commentRepository.save(commentEntity);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();

    }
    return ResponseDto.success();
  }

  @Override
  public ResponseEntity<ResponseDto> patchComment(PatchCommentRequestDto dto, String userId, Integer roomId,
      Integer commentId) {

    try {

      CommentEntity commentEntity = commentRepository.findByCommentIdAndRoomId(commentId, roomId);
      if (commentEntity == null)
        return ResponseDto.noExistComment();

      String commentUser = commentEntity.getUserId();
      boolean isMatched = commentUser.equals(userId);
      if (!isMatched)
        return ResponseDto.noPermission();

      commentEntity.patch(dto);
      commentRepository.save(commentEntity);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }

  @Override
  public ResponseEntity<ResponseDto> deleteComment(String userId, Integer roomId, Integer commentId) {
    try {

      CommentEntity commentEntity = commentRepository.findByCommentIdAndRoomId(commentId, roomId);
      if (commentEntity == null)
        return ResponseDto.noExistComment();

      String commentUser = commentEntity.getUserId();
      boolean isMatched = commentUser.equals(userId);
      if (!isMatched)
        return ResponseDto.noPermission();

      commentEntity.setCommentContents("삭제된 내용입니다. ");
      commentRepository.save(commentEntity);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }

  // @Override
  // public ResponseEntity<ResponseDto> postComment(PostCommentRequestDto dto,
  // Integer roomId) {

  // try {
  // // 1. 방이 존재하는지 확인
  // boolean isRoomExists = discussionRoomRepository.existsByRoomId(roomId);
  // if (!isRoomExists) return ResponseDto.noExistRoom();

  // Integer depth = 0;

  // // if (dto.getParentId() != null) {
  // // Optional<CommentEntity> parentComment =
  // commentRepository.findById(dto.getParentId());
  // // if (parentComment.isEmpty()) return ResponseDto.noExistParentComment();

  // // depth = parentComment.get().getDepth() + 1;
  // // }

  // CommentEntity commentEntity = new CommentEntity(dto, roomId);
  // commentRepository.save(commentEntity);
  // } catch (Exception e) {
  // e.printStackTrace();
  // return ResponseDto.databaseError();
  // }
  // return ResponseDto.success();
  // }

}
