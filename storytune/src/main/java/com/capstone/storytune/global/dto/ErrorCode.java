package com.capstone.storytune.global.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    // 400 Bad Request

    // 401 Unauthorized

    // 403 forbidden
    WRONG_PASSWORD_EXCEPTION(HttpStatus.FORBIDDEN, "잘못된 비밀번호입니다."),

    // 404 not found
    NOT_FOUND_USER_NAME_EXCEPTION(HttpStatus.BAD_REQUEST, "유효하지 않은 아이디입니다."),

    // 500 internal server error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHTTPStatusCode() {
        return httpStatus.value();
    }
}
