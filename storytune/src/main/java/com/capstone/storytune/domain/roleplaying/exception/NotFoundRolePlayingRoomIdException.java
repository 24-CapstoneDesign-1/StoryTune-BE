package com.capstone.storytune.domain.roleplaying.exception;

import com.capstone.storytune.global.dto.ErrorCode;
import com.capstone.storytune.global.exception.BaseException;

public class NotFoundRolePlayingRoomIdException extends BaseException {
    public NotFoundRolePlayingRoomIdException(ErrorCode error) {
        super(error, "[NotFoundRolePlayingRoomIdException]" + error.getMessage());
    }
}
