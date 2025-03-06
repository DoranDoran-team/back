package com.korit.dorandoran.service.implement;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.korit.dorandoran.dto.request.comment.PatchCommentRequestDto;
import com.korit.dorandoran.dto.request.comment.PostCommentRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.entity.CommentsEntity;
import com.korit.dorandoran.entity.DiscussionRoomEntity;
import com.korit.dorandoran.entity.NotificationEntity.NotificationType;
import com.korit.dorandoran.repository.CommentsRepository;
import com.korit.dorandoran.repository.DiscussionRoomRepository;
import com.korit.dorandoran.repository.UserRepository;
import com.korit.dorandoran.service.CommentService;
import com.korit.dorandoran.service.NotificationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImplement implements CommentService {

  private final CommentsRepository commentsRepository;
  private final DiscussionRoomRepository discussionRoomRepository;
  private final NotificationService notificationService;
  private final UserRepository userRepository;

  @Override
  public ResponseEntity<ResponseDto> postComment(PostCommentRequestDto dto, Integer roomId) {

    try {
      // 1. 방 존재 여부 확인
      boolean isRoomExists = discussionRoomRepository.existsByRoomId(roomId);
      if (!isRoomExists) {
        return ResponseDto.noExistRoom();
      }

      Integer depth = 0;
      Integer parentId = dto.getParentId();

      // 2. 상위 댓글이 있을 경우 depth 계산
      if (dto.getParentId() != null) {
        Optional<CommentsEntity> parentCommentOpt = commentsRepository.findByCommentId(parentId);
        if (parentCommentOpt.isEmpty()) {
          return ResponseDto.noExistParentComment();
        }
        depth = parentCommentOpt.get().getDepth() + 1;
      }

      // 3. 댓글 저장
      CommentsEntity commentEntity = new CommentsEntity(dto, roomId, depth);
      commentsRepository.save(commentEntity);

      // 4. 알림 생성
      // 최상위 댓글: 게시물 작성자에게 알림 (댓글 작성자와 게시물 작성자가 다를 때만)
      if (dto.getParentId() == null) {
        DiscussionRoomEntity discussionRoom = discussionRoomRepository.findByRoomId(roomId);
        if (discussionRoom != null) {
          String postOwner = discussionRoom.getUserId();
          if (!postOwner.equals(dto.getUserId())) {
            String message = "당신의 게시물에 새 댓글이 달렸습니다.";
            // additionalInfo: 게시글 상세 페이지 URL 예시
            String additionalInfo = "/gen_disc/" + roomId;
            notificationService.createNotification(postOwner, message, NotificationType.COMMENT_ON_POST,
                additionalInfo);
          }
        }
      } else {
        // 대댓글: 상위 댓글 작성자에게 알림 (댓글 작성자와 상위 댓글 작성자가 다를 때만)
        Optional<CommentsEntity> parentCommentOpt = commentsRepository.findByCommentId(dto.getParentId());
        if (parentCommentOpt.isPresent()) {
          String parentUserId = parentCommentOpt.get().getUserId();
          if (!parentUserId.equals(dto.getUserId())) {
            String message = "당신의 댓글에 새 답글이 달렸습니다.";
            String additionalInfo = "/gen_disc/" + roomId + "#comment" + dto.getParentId();
            notificationService.createNotification(parentUserId, message, NotificationType.REPLY_TO_COMMENT,
                additionalInfo);
          }
        }
      }

      // 언급 처리
      String content = dto.getContents();
      Pattern pattern = Pattern.compile("@(\\S+)");
      Matcher matcher = pattern.matcher(content);
      while (matcher.find()) {
        String mentionToken = matcher.group(1);
        // userId로 검색
        Optional<com.korit.dorandoran.entity.UserEntity> mentionedUserOpt = userRepository.findById(mentionToken);
        if (!mentionedUserOpt.isPresent()) {
          // 없으면 nickname으로 검색
          com.korit.dorandoran.entity.UserEntity userByNick = userRepository.findByNickName(mentionToken);
          if (userByNick != null) {
            mentionedUserOpt = Optional.of(userByNick);
          }
        }
        if (mentionedUserOpt.isPresent()) {
          String mentionedUserId = mentionedUserOpt.get().getUserId();
          if (!mentionedUserId.equals(dto.getUserId())) {
            String message = "댓글에서 당신이 언급되었습니다.";
            String additionalInfo = "/gen_disc/" + roomId;
            notificationService.createNotification(mentionedUserId, message, NotificationType.MENTION, additionalInfo);
          }
        }
      }

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
      CommentsEntity commentsEntity = commentsRepository.findByCommentIdAndRoomId(commentId, roomId);
      if (commentsEntity == null)
        return ResponseDto.noExistComment();

      String commentUser = commentsEntity.getUserId();
      boolean isMatched = commentUser.equals(userId);
      if (!isMatched)
        return ResponseDto.noPermission();

      commentsEntity.patch(dto);
      commentsRepository.save(commentsEntity);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }

  @Override
  public ResponseEntity<ResponseDto> deleteComment(String userId, Integer roomId, Integer commentId) {
    try {
      CommentsEntity commentsEntity = commentsRepository.findByCommentIdAndRoomId(commentId, roomId);
      if (commentsEntity == null)
        return ResponseDto.noExistComment();

      String commentUser = commentsEntity.getUserId();
      boolean isMatched = commentUser.equals(userId);
      if (!isMatched)
        return ResponseDto.noPermission();

      boolean isDelete = commentsEntity.isDeleteStatus();
      commentsEntity.setDeleteStatus(!isDelete);
      commentsRepository.save(commentsEntity);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }
}
