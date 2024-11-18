package com.capstone.storytune.domain.mybook.exception;

import com.capstone.storytune.global.dto.ErrorCode;
import com.capstone.storytune.global.exception.BaseException;

public class NotFoundBookIdException extends BaseException {
    public NotFoundBookIdException(ErrorCode error) {
        super(error, "[NotFoundBookIdException]" + error.getMessage());
    }
}
