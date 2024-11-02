package com.capstone.storytune.global.dto;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{
    private final ErrorCode error;
    public BaseException(ErrorCode error, String message) {
        super(message);
        this.error = error;
    }

    public int getHttpStatus(){
        return error.getHTTPStatusCode();
    }
}
