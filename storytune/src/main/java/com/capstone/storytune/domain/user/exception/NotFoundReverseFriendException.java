package com.capstone.storytune.domain.user.exception;

import com.capstone.storytune.global.dto.ErrorCode;
import com.capstone.storytune.global.exception.BaseException;

public class NotFoundReverseFriendException extends BaseException {
    public NotFoundReverseFriendException(ErrorCode error) {
        super(error, "[NotFoundReverseFriendException]" + error.getMessage());
    }
}
