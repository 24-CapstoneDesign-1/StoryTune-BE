package com.capstone.storytune.global.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {
    // 200 OK
    READ_BOOK_LIST_SUCCESS(OK, "책 목록 조회에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHTTPStatusCode(){
        return httpStatus.value();
    }
}
