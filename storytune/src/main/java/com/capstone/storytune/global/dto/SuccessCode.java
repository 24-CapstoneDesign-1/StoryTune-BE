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
    SIGN_UP_SUCCESS(OK, "회원가입에 성공했습니다."),
    LOGIN_SUCCESS(OK, "로그인에 성공했습니다."),
    READ_BOOKS_SUCCESS(OK, "전체 책 조회에 성공했습니다."),
    READ_MY_BOOKS_SUCCESS(OK, "내가 만든 책 조회에 성공했습니다."),
    READ_RECOMMEND_BOOKS_SUCCESS(OK, "추천 책 조회에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHTTPStatusCode(){
        return httpStatus.value();
    }
}
