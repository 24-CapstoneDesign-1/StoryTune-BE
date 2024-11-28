package com.capstone.storytune.domain.roleplaying.dto.response;

import com.capstone.storytune.domain.roleplaying.domain.Participant;
import lombok.Builder;

@Builder
public record InviteRequestResponse(
        Long participantId,
        Long rolePlayingRoomId,
        String ownerName
) {
    public static InviteRequestResponse of(Participant participant) {
        return InviteRequestResponse.builder()
                .participantId(participant.getId())
                .rolePlayingRoomId(participant.getRolePlayingRoom().getId())
                .ownerName(participant.getRolePlayingRoom().getOwner().getName())
                .build();
    }
}
