package com.capstone.storytune.domain.book.repository;

import com.capstone.storytune.domain.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    // 제목으로 책 검색
    List<Book> findBooksByTitle(String title);
}
