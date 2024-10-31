package com.capstone.storytune.domain.book.repository;

import com.capstone.storytune.domain.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
