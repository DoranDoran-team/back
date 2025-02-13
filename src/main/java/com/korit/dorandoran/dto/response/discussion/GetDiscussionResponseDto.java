package com.korit.dorandoran.dto.response.discussion;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.korit.dorandoran.common.object.Comment;
import com.korit.dorandoran.dto.response.ResponseCode;
import com.korit.dorandoran.dto.response.ResponseDto;
import com.korit.dorandoran.dto.response.ResponseMessage;
import com.korit.dorandoran.repository.resultset.GetDetailDiscussionResultSet;

import lombok.Getter;

@Getter
public class GetDiscussionResponseDto extends ResponseDto {
    
    private GetDetailDiscussionResultSet discussionResultSet;
    private List<Comment> comments;

    public GetDiscussionResponseDto(GetDetailDiscussionResultSet discussionResultSet, List<Comment> comments){
        super(ResponseCode.SUCCESS,ResponseMessage.SUCCESS);

        this.discussionResultSet = discussionResultSet;
        this.comments = comments;
        
    }

    public static ResponseEntity<GetDiscussionResponseDto> success(
        GetDetailDiscussionResultSet discussionResultSet, 
        List<Comment> comments
    ){
        GetDiscussionResponseDto responseBody = new GetDiscussionResponseDto(discussionResultSet, comments);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}

