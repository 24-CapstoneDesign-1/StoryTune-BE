package com.capstone.storytune.domain.roleplaying.dto.response;

import com.capstone.storytune.domain.roleplaying.domain.Participant;
import lombok.Builder;

@Builder
public record ParticipantResponse(
        Long participantId,
        String name
) {
    public static ParticipantResponse of(Participant participant){
        return ParticipantResponse.builder()
                .participantId(participant.getId())
                .name(participant.getUser().getName())
                .build();
    }
}
