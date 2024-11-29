package com.capstone.storytune.domain.roleplaying.dto.request;

public record ParticipantRequest(
        Long rolePlayingRoomId,
        Long userId
) {
}
