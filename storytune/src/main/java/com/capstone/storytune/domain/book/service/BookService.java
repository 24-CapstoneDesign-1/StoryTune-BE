package com.capstone.storytune.domain.book.service;

import com.capstone.storytune.domain.book.domain.MyBook;
import com.capstone.storytune.domain.book.dto.response.BookResponse;
import com.capstone.storytune.domain.book.dto.response.BooksResponse;
import com.capstone.storytune.domain.book.dto.response.MyBookResponse;
import com.capstone.storytune.domain.book.dto.response.MyBooksResponse;
import com.capstone.storytune.domain.book.repository.BookRepository;
import com.capstone.storytune.domain.book.repository.MyBookRepository;
import com.capstone.storytune.domain.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final MyBookRepository myBookRepository;

    public BooksResponse getBooks(){
        List<BookResponse> books = bookRepository.findTop5By()
                .stream()
                .map(BookResponse::of)
                .toList();

        return BooksResponse.of(books);
    }

    public MyBooksResponse getMyBooks(User user){
        List<MyBookResponse> myBooks = myBookRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(MyBookResponse::of)
                .toList();

        return MyBooksResponse.of(myBooks);
    }

    public BooksResponse getRecommendBooks(User user){
        // 특정 사용자가 소유한 모든 Book ID 목록 가져오기
        List<Long> userBookIds = myBookRepository.findBookIdsByUser(user);

        // 특정 사용자가 소유하지 않은 최신 MyBook 목록 가져오기
        List<MyBook> recentMyBooks = myBookRepository.findByUserNotOrderByCreatedDateDesc(user);

        Set<Long> uniqueBookIds = new HashSet<>();
        List<BookResponse> recommendBooks = new ArrayList<>();

        for (MyBook myBook : recentMyBooks) {
            Long bookId = myBook.getId();
            if (!userBookIds.contains(bookId) && uniqueBookIds.add(bookId)) {
                recommendBooks.add(BookResponse.of(myBook.getBook()));
            }
            if (recommendBooks.size() == 5) break;
        }

        return BooksResponse.of(recommendBooks);
    }


}
