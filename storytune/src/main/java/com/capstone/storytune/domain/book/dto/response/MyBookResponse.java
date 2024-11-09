package com.capstone.storytune.domain.book.dto.response;

import com.capstone.storytune.domain.book.domain.MyBook;
import com.capstone.storytune.global.util.TimeConverter;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MyBookResponse(
        Long myBookId,
        String cover,
        String title,
        String updatedAt
) {
    public static MyBookResponse of (MyBook myBook) {
        return MyBookResponse.builder()
                .myBookId(myBook.getId())
                .cover(myBook.getCover())
                .title(myBook.getTitle())
                .updatedAt(getUpdatedAtToString(myBook.getUpdatedAt()))
                .build();
    }

    private static String getUpdatedAtToString(LocalDateTime updatedAt){
        return TimeConverter.transferLocalDateTime(updatedAt);
    }
}
