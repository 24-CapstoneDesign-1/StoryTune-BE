package com.capstone.storytune.global.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micrometer.common.lang.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseResponse<T>{
    private final int status;
    private final String message;

    @JsonInclude(NON_NULL)
    private T result;

    private BaseResponse(){
        throw new IllegalStateException();
    }

    public static <T> BaseResponse<T> success(SuccessCode success){
        return new BaseResponse<>(success.getHTTPStatusCode(), success.getMessage());
    }

    public static <T> BaseResponse<T> success(SuccessCode success, T result){
        return new BaseResponse<>(success.getHTTPStatusCode(), success.getMessage(), result);
    }

    public static <T> BaseResponse<T> error(ErrorCode error){
        return new BaseResponse<>(error.getHTTPStatusCode(), error.getMessage());
    }

    public static <T> BaseResponse<T> error(ErrorCode error, @Nullable String message){
        return new BaseResponse<>(error.getHTTPStatusCode(), message);
    }

    public static <T> BaseResponse<T> error(ErrorCode error, @Nullable String message, @Nullable T result){
        return new BaseResponse<>(error.getHTTPStatusCode(), message, result);
    }

}
