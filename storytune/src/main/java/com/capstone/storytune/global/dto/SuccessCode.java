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
    READ_RECOMMEND_BOOKS_SUCCESS(OK, "추천 책 조회에 성공했습니다."),
    READ_SEARCHED_BOOKS_BY_TITLE_SUCCESS(OK, "제목으로 책 검색에 성공했습니다."),
    READ_MY_BOOK_CONTENT_SUCCESS(OK, "나만의 동화 만들기 상세 페이지 조회에 성공했습니다."),
    READ_USER_BY_USERNAME_SUCCESS(OK, "아이디로 사용자 조회에 성공했습니다."),
    READ_FRIEND_REQUEST_SUCCESS(OK, "친구 요청 수신 목록 조회에 성공했습니다."),
    READ_FRIENDS_SUCCESS(OK, "친구 목록 조회에 성공했습니다."),
    READ_MY_BOOK_CHARACTERS_SUCCESS(OK, "나만의 동화 만들기 등장인물 조회에 성공했습니다."),
    READ_ROLE_PLAYING_ROOM_REQUEST_SUCCESS(OK, "역할놀이 초대 요청 조회에 성공했습니다."),
    READ_ROLE_PLAYING_ROOM_PARTICIPANT_SUCCESS(OK, "함께하는 친구 조회에 성공했습니다."),
    READ_ROLE_PLAYING_ROLE_SUCCESS(OK, "역할놀이 역할 배정에 성공했습니다."),
    READ_ROLE_PLAYING_DETAIL_SUCCESS(OK, "역할놀이 상세 페이지 조회에 성공했습니다."),

    CREATE_MY_BOOK_SUCCESS(OK, "나만의 동화 만들기 생성에 성공했습니다."),
    CREATE_FRIEND_SUCCESS(OK, "친구 추가에 성공했습니다."),
    CREATE_ROLE_PLAYING_ROOM_SUCCESS(OK, "역할놀이 방 생성에 성공했습니다."),
    CREATE_PARTICIPANT_SUCCESS(OK, "역할놀이 방에 친구 초대를 성공했습니다."),
    CREATE_MY_BOOK_CHARACTER_SUCCESS(OK, "나만의 동화 만들기 등장인물 이름 설정에 성공했습니다."),


    UPDATE_MY_BOOK_TOPIC_SUCCESS(OK, "나만의 동화 만들기 주제 생성에 성공했습니다."),
    UPDATE_MY_BOOK_IMAGES_SUCCESS(OK, "나만의 동화 만들기 이미지 생성에 성공했습니다."),
    UPDATE_MY_BOOK_CONTENT_SUCCESS(OK, "나만의 동화 만들기 이야기 생성에 성공했습니다."),
    UPDATE_FRIEND_STATUS_SUCCESS(OK, "친구 요청 응답에 성공했습니다."),
    UPDATE_MY_BOOK_TITLE_SUCCESS(OK, "나만의 동화 만들기 제목 생성에 성공했습니다."),
    UPDATE_MY_BOOK_COVER_SUCCESS(OK, "나만의 동화 만들기 표지 생성에 성공했습니다."),
    UPDATE_PARTICIPANT_REQUEST_SUCCESS(OK, "역할놀이 초대 요청 응답에 성공했습니다."),
    UPDATE_MY_BOOK_COMPLETED_SUCCESS(OK, "나만의 동화 만들기가 완료되었습니다."),
    UPDATE_MY_BOOK_CHARACTER_SUCCESS(OK, "나만의 동화 만들기 등장인물 이름 설정에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHTTPStatusCode(){
        return httpStatus.value();
    }
}
