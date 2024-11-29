package com.capstone.storytune.domain.roleplaying.controller;

import com.capstone.storytune.domain.roleplaying.dto.request.ParticipantRequest;
import com.capstone.storytune.domain.roleplaying.dto.request.ParticipantUpdateRequest;
import com.capstone.storytune.domain.roleplaying.dto.response.*;
import com.capstone.storytune.domain.roleplaying.service.RolePlayingService;
import com.capstone.storytune.domain.user.annotation.CurrentUser;
import com.capstone.storytune.domain.user.domain.User;
import com.capstone.storytune.global.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.capstone.storytune.global.dto.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roleplaying")
public class RolePlayingController {

    private final RolePlayingService rolePlayingService;

    // 롤플레잉 방 만들기
    @Operation(summary = "역할놀이", description = "역할놀이 방 생성 API")
    @PostMapping("")
    public BaseResponse<RolePlayingRoomCreateResponse> createRolePlayingRoom(@CurrentUser User user){
        val result = rolePlayingService.createRolePlayingRoom(user);
        return BaseResponse.success(CREATE_ROLE_PLAYING_ROOM_SUCCESS, result);
    }

    // 친구 초대
    @Operation(summary = "친구 초대하기", description = "역할놀이 방에 친구 초대하는 API")
    @PostMapping("/invite")
    public BaseResponse createInviteRequest(@RequestBody ParticipantRequest request){
        rolePlayingService.createParticipant(request);
        return BaseResponse.success(CREATE_PARTICIPANT_SUCCESS);
    }

    // 요청 목록 조회
    @Operation(summary = "친구 초대 요청 목록 조회", description = "역할놀이 방에 초대된 요청 목록 조회 API")
    @GetMapping("/invite")
    public BaseResponse<List<InviteRequestResponse>> getInviteRequest(@CurrentUser User user){
        val result = rolePlayingService.getInviteRequest(user);
        return BaseResponse.success(READ_ROLE_PLAYING_ROOM_REQUEST_SUCCESS, result);

    }

    // 수락/거절
    @Operation(summary = "친구 초대 요청 응답 (수락/거절)", description = "역할놀이 방에 초대된 요청 목록 응답 API")
    @PatchMapping("/invite/{participantId}")
    public BaseResponse updateInviteRequest(@PathVariable Long participantId, @RequestBody ParticipantUpdateRequest request){
        rolePlayingService.updateInviteRequest(participantId, request);
        return BaseResponse.success(UPDATE_PARTICIPANT_REQUEST_SUCCESS);
    }

    // 함께 하는 친구 조회
    @Operation(summary = "함께하는 친구들", description = "역할놀이 방 참여자 조회하는 API")
    @GetMapping("/{rolePlayingRoomId}/participant")
    public BaseResponse<List<ParticipantResponse>> getParticipant(@PathVariable Long rolePlayingRoomId){
        val result = rolePlayingService.getParticipant(rolePlayingRoomId);
        return BaseResponse.success(READ_ROLE_PLAYING_ROOM_PARTICIPANT_SUCCESS, result);

    }

    // 역할 배정 (myBookId 기반)
    @Operation(summary = "역할 배정이 끝났어요!", description = "역할놀이 역할 배정 결과 조회하는 API")
    @GetMapping("/{rolePlayingRoomId}/{myBookId}/role")
    public BaseResponse<List<RoleResponse>> getRole(@PathVariable Long rolePlayingRoomId, @PathVariable Long myBookId){
        val result = rolePlayingService.getRole(rolePlayingRoomId, myBookId);
        return BaseResponse.success(READ_ROLE_PLAYING_ROLE_SUCCESS, result);
    }

    // 동화 읽기 (페이지 별로) 배정된 참가자 이름, 시나리오 형식 글, 이미지
    @Operation(summary = "대사를 상황에 맞게 읽어보세요!", description = "역할놀이 각 페이지별 이야기 및 이미지 조회하는 API (배정된 인물, 대사 포함)")
    @GetMapping("/{rolePlayingRoomId}/{myBookId}/{pageNum}")
    public BaseResponse<RolePlayingResponse> getRolePlayingDetail(@PathVariable Long rolePlayingRoomId, @PathVariable Long myBookId, @PathVariable int pageNum){
        val result = rolePlayingService.getRolePlayingDetail(rolePlayingRoomId, myBookId, pageNum);
        return BaseResponse.success(READ_ROLE_PLAYING_DETAIL_SUCCESS, result);
    }
}
