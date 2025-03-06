package com.korit.dorandoran.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 검색할 키워드 등을 담는 DTO.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserRequestDto {
    private String keyword;
}
