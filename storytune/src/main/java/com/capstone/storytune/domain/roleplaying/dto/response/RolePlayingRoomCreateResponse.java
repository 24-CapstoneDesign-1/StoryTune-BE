package com.capstone.storytune.domain.roleplaying.dto.response;

import com.capstone.storytune.domain.roleplaying.domain.RolePlayingRoom;
import lombok.Builder;

@Builder
public record RolePlayingRoomCreateResponse(
        Long rolePlayingRoomId
) {
    public static RolePlayingRoomCreateResponse of(RolePlayingRoom rolePlayingRoom) {
        return RolePlayingRoomCreateResponse.builder()
                .rolePlayingRoomId(rolePlayingRoom.getId())
                .build();
    }
}
