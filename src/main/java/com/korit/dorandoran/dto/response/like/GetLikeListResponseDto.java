package com.korit.dorandoran.dto.response.like;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.korit.dorandoran.dto.response.ResponseCode;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.ResponseMessage;


import lombok.Getter;

@Getter
public class GetLikeListResponseDto extends ResponseDto {
    
    private Integer roomId;
    private boolean isLikePost;
    private List<Map<String, Object>> isLikeComment;

    public GetLikeListResponseDto(Integer roomId, boolean isLikePost, List<Map<String, Object>> isLikeComment){
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        
        this.roomId = roomId;
        this.isLikeComment = isLikeComment;
        this.isLikeComment = isLikeComment;
    }

    public static ResponseEntity<GetLikeListResponseDto> success (
        Integer roomId,
        boolean isLikePost,
        List <Map<String, Object>> isLikeComment
    ){
        GetLikeListResponseDto responseBody = new GetLikeListResponseDto(roomId, isLikePost, isLikeComment);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
