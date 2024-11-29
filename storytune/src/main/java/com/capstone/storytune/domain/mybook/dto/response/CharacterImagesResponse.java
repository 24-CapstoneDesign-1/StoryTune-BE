package com.capstone.storytune.domain.mybook.dto.response;

import com.capstone.storytune.domain.mybook.domain.MyBookCharacter;
import lombok.Builder;

import java.util.List;

@Builder
public record CharacterImagesResponse(
        List<Long> myBookCharacterIds
) {
    public static CharacterImagesResponse of(List<MyBookCharacter> characters) {
        List<Long> myBookCharacterIds = characters.stream()
                .map(MyBookCharacter::getId)
                .toList();
        return CharacterImagesResponse.builder()
                .myBookCharacterIds(myBookCharacterIds)
                .build();
    }
}
