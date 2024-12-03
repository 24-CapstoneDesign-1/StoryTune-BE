package com.capstone.storytune.domain.mybook.dto.response;

import com.capstone.storytune.domain.mybook.domain.MyBookContent;
import lombok.Builder;

@Builder
public record MyBookContentResponse(
        Long myBookContentId,
        String title,
        String image,
        String previousContent
) {
    public static MyBookContentResponse of (MyBookContent myBookContent, String previousContent){
        return MyBookContentResponse.builder()
                .myBookContentId(myBookContent.getId())
                .title(myBookContent.getMyBook().getTitle())
                .image(myBookContent.getImage())
                .previousContent(previousContent)
                .build();
    }
}
