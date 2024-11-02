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
    LOGIN_SUCCESS(OK, "로그인에 성공했습니다.");


    private final HttpStatus httpStatus;
    private final String message;

    public int getHTTPStatusCode(){
        return httpStatus.value();
    }
}
