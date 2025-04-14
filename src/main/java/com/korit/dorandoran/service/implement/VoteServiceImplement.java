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
import com.korit.dorandoran.repository.UserRepository;
import com.korit.dorandoran.repository.VoteRepository;
import com.korit.dorandoran.service.VoteService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteServiceImplement implements VoteService {

    private final UserRepository userRepository;
    private final DiscussionRoomRepository discussionRoomRepository;
    private final VoteRepository voteRepository;

    @Override
    public ResponseEntity<ResponseDto> postVote(PostVoteRequestDto dto, String userId, String roomId) {
        try {
            // 사용자 존재 확인
            boolean isUser = userRepository.existsByUserId(userId);
            if (!isUser)
                return ResponseDto.noPermission();

            String finalRoomId;
            // roomId가 숫자(DiscussionRoom)로 구성되었는지 체크
            if (roomId.matches("\\d+")) {
                // 숫자형인 경우: 정수로 파싱하고, 존재여부 확인 후 toString()으로 저장
                int intRoomId = Integer.parseInt(roomId);
                boolean isRoom = discussionRoomRepository.existsByRoomId(intRoomId);
                if (!isRoom)
                    return ResponseDto.noExistRoom();
                finalRoomId = String.valueOf(intRoomId);
                boolean isVote = voteRepository.existsByRoomIdAndUserId(finalRoomId, userId);
                if (isVote)
                    return ResponseDto.duplicatedVote();
                VoteEntity voteEntity = new VoteEntity(dto, intRoomId, userId);
                voteRepository.save(voteEntity);
            } else {
                // 숫자형이 아닌 경우: MongoDB 채팅방으로 간주하고 roomId를 그대로 사용
                finalRoomId = roomId;
                boolean isVote = voteRepository.existsByRoomIdAndUserId(finalRoomId, userId);
                if (isVote)
                    return ResponseDto.duplicatedVote();
                VoteEntity voteEntity = new VoteEntity(dto, finalRoomId, userId, true);
                voteRepository.save(voteEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetVoteResultResponseDto> getVoteResult(String roomId) {
        List<VoteEntity> voteResult = new ArrayList<>();
        try {
            String finalRoomId;
            if (roomId.matches("\\d+")) {
                int intRoomId = Integer.parseInt(roomId);
                boolean isRoom = discussionRoomRepository.existsByRoomId(intRoomId);
                if (!isRoom)
                    return ResponseDto.noExistRoom();
                finalRoomId = String.valueOf(intRoomId);
                voteResult = voteRepository.getResult(finalRoomId);
            } else {
                finalRoomId = roomId;
                voteResult = voteRepository.getResult(finalRoomId);
            }
            if (voteResult == null) {
                voteResult = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetVoteResultResponseDto.success(voteResult);
    }
}
