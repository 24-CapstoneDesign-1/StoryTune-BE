package com.capstone.storytune.domain.mybook.exception;

import com.capstone.storytune.global.dto.ErrorCode;
import com.capstone.storytune.global.exception.BaseException;

public class NotFoundMyBookContentException extends BaseException {
    public NotFoundMyBookContentException(ErrorCode error){
        super(error, "[NotFoundMyBookContentException]" + error.getMessage());
    }
}
