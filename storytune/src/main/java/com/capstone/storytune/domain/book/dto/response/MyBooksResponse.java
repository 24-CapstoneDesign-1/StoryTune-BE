package com.capstone.storytune.domain.book.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MyBooksResponse(
        List<MyBookResponse> myBooks
) {
    public static MyBooksResponse of(List<MyBookResponse> myBooks) {
        return MyBooksResponse.builder()
                .myBooks(myBooks)
                .build();
    }
}
