package com.capstone.storytune.domain.mybook.exception;

import com.capstone.storytune.global.dto.ErrorCode;
import com.capstone.storytune.global.exception.BaseException;

public class FailedUploadImageException extends BaseException {
    public FailedUploadImageException(ErrorCode error){
        super(error, "[FailedUploadImageException]" + error.getMessage());
    }
}
