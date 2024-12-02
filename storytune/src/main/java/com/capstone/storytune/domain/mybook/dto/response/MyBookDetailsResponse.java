package com.capstone.storytune.domain.mybook.dto.response;

import com.capstone.storytune.domain.mybook.domain.MyBook;
import lombok.Builder;

import java.util.List;

import static com.capstone.storytune.global.util.TimeConverter.transferLocalDateTime;

@Builder
public record MyBookDetailsResponse(
        String title,
        String createdAt,
        List<MyBookDetailResponse> details
) {
    public static MyBookDetailsResponse of(MyBook myBook, List<MyBookDetailResponse> details) {
        return MyBookDetailsResponse.builder()
                .title(myBook.getTitle())
                .createdAt(transferLocalDateTime(myBook.getCreatedAt()))
                .details(details)
                .build();
    }
}
