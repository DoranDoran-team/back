package com.korit.dorandoran.dto.response.vote;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.korit.dorandoran.dto.response.ResponseCode;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.ResponseMessage;
import com.korit.dorandoran.entity.VoteEntity;

import lombok.Getter;

@Getter
public class GetVoteResultResponseDto extends ResponseDto {
    
    private List<VoteEntity> voteResult;

    public GetVoteResultResponseDto(List<VoteEntity> voteResult){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.voteResult = voteResult;
    }

    public static ResponseEntity<GetVoteResultResponseDto> success(List<VoteEntity> voteResult){
        GetVoteResultResponseDto responseBody = new GetVoteResultResponseDto(voteResult);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
