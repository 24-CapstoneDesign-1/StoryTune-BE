package com.capstone.storytune.domain.mybook.dto.response;

import com.capstone.storytune.domain.mybook.domain.MyBookContent;
import lombok.Builder;

@Builder
public record MyBookDetailResponse(
        int pageNum,
        String image,
        String content_scenario,
        String content_story
) {
    public static MyBookDetailResponse of(MyBookContent myBookContent) {
        return MyBookDetailResponse.builder()
                .pageNum(myBookContent.getPage())
                .image(myBookContent.getImage())
                .content_scenario(myBookContent.getContent_scenario())
                .content_story(myBookContent.getContent_story())
                .build();
    }
}
