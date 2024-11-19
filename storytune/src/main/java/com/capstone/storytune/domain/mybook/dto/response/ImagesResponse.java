package com.capstone.storytune.domain.mybook.dto.response;

import com.capstone.storytune.domain.mybook.domain.MyBookContent;
import lombok.Builder;

import java.util.List;

@Builder
public record ImagesResponse(
        List<Long> myBookContentIds
) {
    public static ImagesResponse of(List<MyBookContent> myBookContents) {
        List<Long> ids = myBookContents.stream()
                .map(MyBookContent::getId)
                .toList();

        return ImagesResponse.builder()
                .myBookContentIds(ids)
                .build();
    }
}
