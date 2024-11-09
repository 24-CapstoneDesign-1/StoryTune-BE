package com.capstone.storytune.domain.user.exception;

import com.capstone.storytune.global.dto.ErrorCode;
import com.capstone.storytune.global.exception.BaseException;

public class NotFoundUserIdException extends BaseException {
    public NotFoundUserIdException(ErrorCode error) {
        super(error, "[NotFoundUserIdException]" + error.getMessage());
    }
}
