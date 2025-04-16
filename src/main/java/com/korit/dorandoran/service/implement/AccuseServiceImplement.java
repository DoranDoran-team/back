package com.korit.dorandoran.service.implement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.korit.dorandoran.common.object.Accuse;
import com.korit.dorandoran.common.object.AccuseStatus;
import com.korit.dorandoran.dto.request.accuse.PatchBlackListRequestDto;
import com.korit.dorandoran.dto.request.accuse.PostAccuseRequestDto;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.accuse.GetAccuseDetailResponseDto;
import com.korit.dorandoran.dto.response.accuse.GetAccuseListResponseDto;
import com.korit.dorandoran.dto.response.accuse.GetBlackListResponseDto;
import com.korit.dorandoran.entity.AccuseEntity;
import com.korit.dorandoran.entity.UserEntity;
import com.korit.dorandoran.repository.AccuseRepository;
import com.korit.dorandoran.repository.DiscussionRoomRepository;
import com.korit.dorandoran.repository.UserRepository;
import com.korit.dorandoran.repository.resultset.GetAccuseResultSet;
import com.korit.dorandoran.service.AccuseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccuseServiceImplement implements AccuseService {

  private final AccuseRepository accuseRepository;
  private final DiscussionRoomRepository discussionRoomRepository;
  private final UserRepository userRepository;

  @Override
  public ResponseEntity<ResponseDto> postAccuse(PostAccuseRequestDto dto) {

    // 신고할 대상(replyId, postId)이 없는 경우 예외 처리
    if (dto.getReplyId() == null && dto.getPostId() == null) {
      return ResponseDto.noExistedTarget();
    }

    if (dto.getPostId() != null) {
      String postOwnerId = discussionRoomRepository.findUserIdByRoomId(dto.getPostId());
      if (postOwnerId != null && postOwnerId.equals(dto.getUserId())) {
        return ResponseDto.noSelfAccuse();
      }
    }

    // 댓글 또는 게시글 중복 신고 여부 확인
    boolean alreadyReported = false;
    if (dto.getReplyId() != null) {
      alreadyReported = accuseRepository.existsByUserIdAndReportTypeAndReplyId(
          dto.getUserId(), dto.getReportType(), dto.getReplyId());
    } else {
      alreadyReported = accuseRepository.existsByUserIdAndReportTypeAndPostId(
          dto.getUserId(), dto.getReportType(), dto.getPostId());
    }

    // 중복 신고일 경우 에러 응답 반환
    if (alreadyReported) {
      return ResponseDto.duplicatedAccuse();
    }

    // 신고 저장
    try {
      AccuseEntity accuseEntity = new AccuseEntity(dto);
      accuseRepository.save(accuseEntity);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success();
  }

  @Override
  public ResponseEntity<? super GetAccuseListResponseDto> getAccuseList(String userId) {

    UserEntity user = userRepository.findByUserId(userId);
    if (user == null) {
      return ResponseDto.databaseError();
    }
    if (user.getRole() != true) {
      return ResponseDto.noPermission();
    }

    List<Accuse> accuses = new ArrayList<>();

    try {

      List<AccuseEntity> accuseEntities = accuseRepository
          .findAllByAccuseStatusOrderByAccuseIdAsc(AccuseStatus.PENDING);
      for (AccuseEntity accuseEntity : accuseEntities) {
        Accuse accuse = new Accuse(accuseEntity);
        accuses.add(accuse);
      }

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetAccuseListResponseDto.success(accuses);
  }

  @Override
  public ResponseEntity<? super GetAccuseDetailResponseDto> getAccuseDetail(Integer accuseId) {
    GetAccuseResultSet getAccuseResultSet;

    try {

      getAccuseResultSet = accuseRepository.findAccuseDetail(accuseId);

      if (getAccuseResultSet == null) {
        return ResponseDto.noHaveAccuse();
      }

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetAccuseDetailResponseDto.success(getAccuseResultSet);
  }

  @Override
  public ResponseEntity<ResponseDto> approveAccuse(Integer accuseId) {

    try {

      AccuseEntity accuseEntity = accuseRepository.findByAccuseId(accuseId);

      if (accuseEntity == null) {
        return ResponseDto.noHaveAccuse();
      }

      if (accuseEntity.getAccuseStatus() == AccuseStatus.APPROVED) {
        return ResponseDto.alreadyApproved();
      }

      String userId = accuseEntity.getAccuseUserId();
      accuseEntity.setAccuseStatus(AccuseStatus.APPROVED);
      accuseRepository.save(accuseEntity);

      UserEntity userEntity = userRepository.findByUserId(userId);

      if (userId == null) {
        return ResponseDto.noExistUserId();
      }

      userEntity.setAccuseCount(userEntity.getAccuseCount() + 1);

      /*
       * 신고 횟수가 5회 이상 되었을 경우,
       * 1. accuse_count 0 으로 초기화
       * 2. accuse_state true로 상태 전환 및 활동 중지 처리
       * 3. accuse_time 을 해당 로직 실행 시간으로 설정 후, 일주일 뒤에 자동으로
       * 활동 중지 해제
       */
      if (userEntity.getAccuseCount() >= 5) {
        userEntity.setAccuseCount(0);
        userEntity.setAccuseState(true);

            // 현재 시간을 yyyy.MM.dd HH:mm:ss 형식으로
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
            String formattedDate = now.format(formatter);
            userEntity.setAccuseTime(formattedDate);
        }

      userRepository.save(userEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success();
  }

  // 7일이 지났는지 시스템에서 자동으로 검사
  @Scheduled(cron = "0 0 3 * * *") // 매일 새벽 3시 실행
  @Transactional
  public void resetAccuseStates() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    userRepository.findAll().forEach(user -> {
        if (user.getAccuseState() && user.getAccuseTime() != null) {
            try {
                LocalDateTime accuseTime = LocalDateTime.parse(user.getAccuseTime(), formatter);
                long daysBetween = ChronoUnit.DAYS.between(accuseTime, LocalDateTime.now());

                if (daysBetween >= 7) {
                    user.setAccuseState(false);
                    user.setAccuseTime(null); // 초기화
                    userRepository.save(user);
                    System.out.println("자동 리셋: 사용자 " + user.getUserId() + "의 accuse_state 초기화 완료");
                }
            } catch (Exception e) {
                System.err.println("날짜 파싱 오류: " + user.getUserId());
                e.printStackTrace();
            }
        }
    });
}


  @Override
  public ResponseEntity<ResponseDto> rejectedAccuse(Integer accuseId) {

    try {

      AccuseEntity accuseEntity = accuseRepository.findByAccuseId(accuseId);

      if (accuseEntity == null) {
        return ResponseDto.noHaveAccuse();
      }

      if (accuseEntity.getAccuseStatus() == AccuseStatus.REJECTED) {
        return ResponseDto.rejectedApproved();
      }

      accuseEntity.setAccuseStatus(AccuseStatus.REJECTED);
      accuseRepository.save(accuseEntity);

    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success();
  }

  // 활동 중지 상태(accuseState = true)인 유저 가져오기
  @Override
  public ResponseEntity<? super GetBlackListResponseDto> getBlackList() {
    List<UserEntity> userEntities = new ArrayList<>();
    try {
      userEntities = userRepository.findByAccuseState(true);
    } catch (Exception e) {
      return ResponseDto.databaseError();
    }
    return GetBlackListResponseDto.success(userEntities);
  }

  // 활동 중지 취소
  @Override
  public ResponseEntity<ResponseDto> cancelBlackList(PatchBlackListRequestDto dto) {
    String userId = dto.getUserId();
    try {
      UserEntity userEntity = userRepository.findByUserId(userId);
      if (userEntity == null) return ResponseDto.noExistUserId();

      userEntity.setAccuseState(false);
      userEntity.setAccuseTime(null);
      userRepository.save(userEntity);

    } catch (Exception e) {
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }

  // 활동 중지 설정
  @Override
  public ResponseEntity<ResponseDto> setBlackList(PatchBlackListRequestDto dto) {
    String userId = dto.getUserId();
    try {
      UserEntity userEntity = userRepository.findByUserId(userId);
      if (userEntity == null) return ResponseDto.noExistUserId();

      userEntity.setAccuseCount(0);
      userEntity.setAccuseState(true);
      
      // 현재 시간을 yyyy.MM.dd HH:mm:ss 형식으로
      LocalDateTime now = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
      String formattedDate = now.format(formatter);
      userEntity.setAccuseTime(formattedDate);

      userRepository.save(userEntity);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseDto.databaseError();
    }
    return ResponseDto.success();
  }
}
