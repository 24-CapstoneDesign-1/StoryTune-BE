package com.capstone.storytune.domain.user.exception;


import com.capstone.storytune.global.exception.BaseException;
import com.capstone.storytune.global.dto.ErrorCode;

public class WrongPasswordException extends BaseException {
    public WrongPasswordException(ErrorCode error){
        super(error, "[WrongPasswordException]" + error.getMessage());
    }
}
