package com.capstone.storytune.domain.user.controller;

import com.capstone.storytune.domain.user.dto.request.LoginRequest;
import com.capstone.storytune.domain.user.dto.request.SignupRequest;
import com.capstone.storytune.domain.user.dto.response.FriendSearchResponse;
import com.capstone.storytune.domain.user.dto.response.LoginResponse;
import com.capstone.storytune.domain.user.service.UserService;
import com.capstone.storytune.global.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import static com.capstone.storytune.global.dto.SuccessCode.*;

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

    // 친구 검색 - 아이디
    @Operation(summary = "친구 검색하기", description = "친구 아이디로 검색하는 API")
    @GetMapping("/friend")
    public BaseResponse<FriendSearchResponse> getFriend(@RequestParam String username){
        val result = userService.getUserByUsername(username);
        return BaseResponse.success(READ_USER_BY_USERNAME_SUCCESS, result);
    }

    // 친구 추가


}
