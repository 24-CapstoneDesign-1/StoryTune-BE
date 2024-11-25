package com.capstone.storytune.domain.user.controller;

import com.capstone.storytune.domain.user.annotation.CurrentUser;
import com.capstone.storytune.domain.user.domain.User;
import com.capstone.storytune.domain.user.dto.request.FriendUpdateRequest;
import com.capstone.storytune.domain.user.dto.request.LoginRequest;
import com.capstone.storytune.domain.user.dto.request.SignupRequest;
import com.capstone.storytune.domain.user.dto.response.FriendRequestResponse;
import com.capstone.storytune.domain.user.dto.response.FriendResponse;
import com.capstone.storytune.domain.user.dto.response.UserSearchResponse;
import com.capstone.storytune.domain.user.dto.response.LoginResponse;
import com.capstone.storytune.domain.user.service.UserService;
import com.capstone.storytune.global.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    // 사용자 검색 - 아이디
    @Operation(summary = "친구 검색하기", description = "사용자 아이디로 사용자를 검색하는 API")
    @GetMapping("/friend/search")
    public BaseResponse<UserSearchResponse> getUserByUsername(@RequestParam String username){
        val result = userService.getUserByUsername(username);
        return BaseResponse.success(READ_USER_BY_USERNAME_SUCCESS, result);
    }

    // 친구 추가 - userId
    @Operation(summary = "친구 추가", description = "친구 추가하는 API (userId = 추가할 친구의 userId)")
    @PostMapping("/friend/{userId}")
    public BaseResponse createFriend(@PathVariable Long userId, @CurrentUser User user){
        userService.createFriend(userId, user);
        return BaseResponse.success(CREATE_FRIEND_SUCCESS);
    }

    // 친구 요청 목록 조회
    @Operation(summary = "친구 요청 수신 목록 조회", description = "내가 받은 친구 요청 목록을 조회하는 API (friendId, 친구 이름, 친구 아이디)")
    @GetMapping("/friend/request")
    public BaseResponse<List<FriendRequestResponse>> getFriendRequest(@CurrentUser User user){
        val result = userService.getFriendRequest(user);
        return BaseResponse.success(READ_FRIEND_REQUEST_SUCCESS, result);
    }

    // 친구 요청 응답 (수락/거절)
    @Operation(summary = "친구 요청 응답 (수락/거절)", description = "내가 받은 친구 요청에 응답하는 API")
    @PatchMapping("/friend/request/{friendId}")
    public BaseResponse updateFriendStatus(@PathVariable Long friendId, @RequestBody FriendUpdateRequest request){
        userService.updateFriendStatus(friendId, request);
        return BaseResponse.success(UPDATE_FRIEND_STATUS_SUCCESS);
    }

    // 친구 목록 조회
    @Operation(summary = "친구 목록 조회 (친구 초대 시 사용)", description = "내 친구 목록 조회하는 API")
    @GetMapping("/friend")
    public BaseResponse<List<FriendResponse>> getFriends(@CurrentUser User user){
        val result = userService.getFriends(user);
        return BaseResponse.success(READ_FRIENDS_SUCCESS, result);
    }

}
