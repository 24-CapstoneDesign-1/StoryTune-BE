package com.capstone.storytune.domain.user.controller;

import com.capstone.storytune.domain.user.dto.request.LoginRequest;
import com.capstone.storytune.domain.user.dto.request.SignupRequest;
import com.capstone.storytune.domain.user.dto.response.LoginResponse;
import com.capstone.storytune.domain.user.service.UserService;
import com.capstone.storytune.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.capstone.storytune.global.dto.SuccessCode.LOGIN_SUCCESS;
import static com.capstone.storytune.global.dto.SuccessCode.SIGN_UP_SUCCESS;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public BaseResponse signUp(@RequestBody SignupRequest request) {
        userService.signUp(request);
        return BaseResponse.success(SIGN_UP_SUCCESS);
    }

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        val result = userService.login(request);
        return BaseResponse.success(LOGIN_SUCCESS, result);
    }
}
