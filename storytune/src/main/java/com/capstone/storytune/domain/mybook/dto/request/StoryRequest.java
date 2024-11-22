package com.capstone.storytune.domain.mybook.dto.request;

public record MyBookContentUpdateRequest(
        String content,
        boolean isLine,
        String character
) {
}
