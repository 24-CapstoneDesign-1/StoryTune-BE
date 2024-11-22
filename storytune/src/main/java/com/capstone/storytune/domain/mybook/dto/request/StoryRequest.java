package com.capstone.storytune.domain.mybook.dto.request;

public record StoryRequest(
        String content,
        boolean isLine,
        Long myBookCharacterId
) {
}
