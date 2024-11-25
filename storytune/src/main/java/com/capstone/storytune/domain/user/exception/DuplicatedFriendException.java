package com.capstone.storytune.domain.user.exception;

import com.capstone.storytune.global.dto.ErrorCode;
import com.capstone.storytune.global.exception.BaseException;

public class DuplicatedFriendException extends BaseException {
    public DuplicatedFriendException(ErrorCode error) {
        super(error, "[DuplicatedFriend]" + error.getMessage());
    }
}
