package com.capstone.storytune.domain.mybook.dto.response;

import com.capstone.storytune.domain.mybook.domain.MyBookCharacter;
import lombok.Builder;

@Builder
public record CharacterResponse(
        Long myBookCharacterId,
        String name
) {
    public static CharacterResponse of(MyBookCharacter character) {
        return CharacterResponse.builder()
                .myBookCharacterId(character.getId())
                .name(character.getName())
                .build();
    }
}
