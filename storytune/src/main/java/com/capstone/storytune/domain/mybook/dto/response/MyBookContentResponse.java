package com.capstone.storytune.domain.mybook.dto.response;

import com.capstone.storytune.domain.mybook.domain.MyBookContent;
import lombok.Builder;

@Builder
public record MyBookContentResponse(
        Long myBookContentId,
        String image,
        String guide
) {
    public static MyBookContentResponse of (MyBookContent myBookContent){
        return MyBookContentResponse.builder()
                .myBookContentId(myBookContent.getId())
                .image(myBookContent.getImage())
                .guide(myBookContent.getGuide())
                .build();
    }
}
