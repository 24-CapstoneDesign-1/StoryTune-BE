package com.capstone.storytune.domain.roleplaying.exception;

import com.capstone.storytune.global.dto.ErrorCode;
import com.capstone.storytune.global.exception.BaseException;

public class NotFoundParticipantIdException extends BaseException {
    public NotFoundParticipantIdException(ErrorCode error) {
        super(error, "[NotFoundParticipantIdException]" + error.getMessage());
    }
}
