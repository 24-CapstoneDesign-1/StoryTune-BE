package com.capstone.storytune.domain.mybook.repository;

import com.capstone.storytune.domain.mybook.domain.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {
}
