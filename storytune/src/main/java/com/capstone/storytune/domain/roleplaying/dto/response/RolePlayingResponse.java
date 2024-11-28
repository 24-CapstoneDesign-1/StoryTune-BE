package com.capstone.storytune.domain.roleplaying.dto.response;

import com.capstone.storytune.domain.mybook.domain.MyBookContent;
import com.capstone.storytune.domain.roleplaying.domain.Participant;
import lombok.Builder;

@Builder
public record RolePlayingResponse(
        String name,
        String content_scenario,
        String image
) {
    public static RolePlayingResponse of(Participant participant, MyBookContent myBookContent) {
        return RolePlayingResponse.builder()
                .name(participant.getUser().getName())
                .content_scenario(myBookContent.getContent_scenario())
                .image(myBookContent.getImage())
                .build();
    }
}
