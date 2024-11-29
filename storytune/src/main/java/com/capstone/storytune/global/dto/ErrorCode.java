package com.capstone.storytune.global.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    // 400 Bad Request
    DUPLICATED_FRIEND_EXCEPTION(HttpStatus.BAD_REQUEST, "중복된 친구관계입니다."),

    // 401 Unauthorized
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),

    // 403 forbidden
    WRONG_PASSWORD_EXCEPTION(HttpStatus.FORBIDDEN, "잘못된 비밀번호입니다."),

    // 404 not found
    NOT_FOUND_USER_NAME_EXCEPTION(HttpStatus.BAD_REQUEST, "유효하지 않은 아이디입니다."),
    NOT_FOUND_USER_ID_EXCEPTION(HttpStatus.BAD_REQUEST, "유효하지 않은 userId입니다."),
    NOT_FOUND_BOOK_ID_EXCEPTION(HttpStatus.BAD_REQUEST, "유효하지 않은 bookId입니다."),
    NOT_FOUND_MY_BOOK_ID_EXCEPTION(HttpStatus.BAD_REQUEST, "유효하지 않은 myBookId입니다."),
    NOT_FOUND_MY_BOOK_CONTENT_EXCEPTION(HttpStatus.BAD_REQUEST, "유효하지 않은 myBookId와 pageNum 입니다."),
    NOT_FOUND_MY_BOOK_CHARACTER_EXCEPTION(HttpStatus.BAD_REQUEST, "유효하지 않은 myBookCharacterId 입니다."),
    NOT_FOUND_FRIEND_EXCEPTION(HttpStatus.BAD_REQUEST, "유효하지 않은 friendId 입니다."),
    NOT_FOUND_REVERSE_FRIEND_EXCEPTION(HttpStatus.BAD_REQUEST, "반대 방향 Friend 객체를 찾을 수 없습니다."),
    NOT_FOUND_ROLE_PLAYING_ROOM_ID_EXCEPTION(HttpStatus.BAD_REQUEST, "유효하지 않은 rolePlayingroomId 입니다."),
    NOT_FOUND_PARTICIPANT_ID_EXCEPTION(HttpStatus.BAD_REQUEST, "유효하지 않은 participantId입니다."),


    // 500 internal server error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다.");


    private final HttpStatus httpStatus;
    private final String message;

    public int getHTTPStatusCode() {
        return httpStatus.value();
    }
}
