package com.capstone.storytune.domain.mybook.exception;

import com.capstone.storytune.global.dto.ErrorCode;
import com.capstone.storytune.global.exception.BaseException;

public class NotFoundMyBookCharacterException extends BaseException {
    public NotFoundMyBookCharacterException(ErrorCode error){
        super(error, "[NotFoundMyBookCharacterException]" + error.getMessage());
    }
}
