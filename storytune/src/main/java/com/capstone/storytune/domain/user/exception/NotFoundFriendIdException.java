package com.capstone.storytune.domain.user.exception;

import com.capstone.storytune.global.dto.ErrorCode;
import com.capstone.storytune.global.exception.BaseException;

public class NotFoundFriendIdException extends BaseException {
    public NotFoundFriendIdException(ErrorCode error) {
        super(error, "[NotFoundFriendIdException]" + error.getMessage());
    }
}
