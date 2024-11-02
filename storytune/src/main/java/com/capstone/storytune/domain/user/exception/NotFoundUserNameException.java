package com.capstone.storytune.domain.user.exception;

import com.capstone.storytune.global.dto.BaseException;
import com.capstone.storytune.global.dto.ErrorCode;

public class NotFoundUserNameException extends BaseException {
    public NotFoundUserNameException(ErrorCode error) {
        super(error, "[NotFoundUserNameException]" + error.getMessage());
    }
}
