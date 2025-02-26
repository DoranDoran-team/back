package com.korit.dorandoran.dto.request.vote;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostVoteRequestDto {
    
    @NotBlank
    private String  voteChoice;
}
