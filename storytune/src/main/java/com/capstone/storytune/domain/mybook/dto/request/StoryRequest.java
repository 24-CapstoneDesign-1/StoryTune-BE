package com.capstone.storytune.domain.mybook.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record StoryRequest(
        MultipartFile audio,
        boolean isLine,
        Long myBookCharacterId
) {
}
