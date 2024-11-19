package com.capstone.storytune.domain.book.service;

import com.capstone.storytune.domain.book.dto.response.BookResponse;
import com.capstone.storytune.domain.book.dto.response.BooksResponse;
import com.capstone.storytune.domain.book.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;

    public BooksResponse getBooks(){
        List<BookResponse> books = bookRepository.findAll()
                .stream()
                .map(BookResponse::of)
                .toList();

        return BooksResponse.of(books);
    }

    public BooksResponse getBooksByTitle(String title){
        List<BookResponse> searchedBooks = bookRepository.findBooksByTitle(title)
                .stream()
                .map(BookResponse::of)
                .toList();

        return BooksResponse.of(searchedBooks);
    }
}
