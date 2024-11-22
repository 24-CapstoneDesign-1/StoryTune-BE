package com.capstone.storytune.domain.mybook.dto.response;

import lombok.Builder;

@Builder
public record MyBookDetailResponse(
        Long myBookContentId,
        String image,
        String guide
) {
}
