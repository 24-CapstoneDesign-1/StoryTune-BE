package com.capstone.storytune.domain.book.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record BooksResponse(
        List<BookResponse> books
) {
    public static BooksResponse of (List<BookResponse> books){
        return BooksResponse.builder()
                .books(books)
                .build();
    }
}
