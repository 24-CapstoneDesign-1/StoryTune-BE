package com.capstone.storytune.domain.book.dto.response;

import com.capstone.storytune.domain.book.domain.Book;
import lombok.Builder;

@Builder
public record BookResponse (
        Long bookId,
        String cover,
        String title,
        String author
){
    public static BookResponse of (Book book){
        return BookResponse.builder()
                .bookId(book.getId())
                .cover(book.getCover())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }
}
