package com.capstone.storytune.domain.book.repository;

import com.capstone.storytune.domain.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // 상위 5개의 책만 반환
    List<Book> findTop5By();
}
