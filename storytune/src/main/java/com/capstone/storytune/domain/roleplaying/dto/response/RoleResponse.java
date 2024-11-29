package com.capstone.storytune.domain.roleplaying.dto.response;

import com.capstone.storytune.domain.mybook.domain.MyBookCharacter;
import com.capstone.storytune.domain.roleplaying.domain.Participant;
import lombok.Builder;

@Builder
public record RoleResponse(
        String name,
        String characterName,
        String image
) {
    public static RoleResponse of(Participant participant){
        return RoleResponse.builder()
                .name(participant.getUser().getName())
                .characterName(participant.getCharacter().getName())
                .image(participant.getCharacter().getImage())
                .build();
    }
}
