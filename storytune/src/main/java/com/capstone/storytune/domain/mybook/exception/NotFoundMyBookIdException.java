package com.capstone.storytune.domain.mybook.exception;

import com.capstone.storytune.global.dto.ErrorCode;
import com.capstone.storytune.global.exception.BaseException;

public class NotFoundMyBookIdException extends BaseException {
    public NotFoundMyBookIdException(ErrorCode error) {
        super(error, "[NotFoundMyBookIdException]" + error.getMessage());
    }
}
