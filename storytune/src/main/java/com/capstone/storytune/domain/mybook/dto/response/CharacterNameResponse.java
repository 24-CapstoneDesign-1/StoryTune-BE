package com.capstone.storytune.domain.mybook.dto.response;

import com.capstone.storytune.domain.mybook.domain.MyBookCharacter;
import lombok.Builder;

@Builder
public record CharacterNameResponse(
        String name
) {
    public static CharacterNameResponse of(MyBookCharacter character) {
        return CharacterNameResponse.builder()
                .name(character.getName())
                .build();
    }
}
