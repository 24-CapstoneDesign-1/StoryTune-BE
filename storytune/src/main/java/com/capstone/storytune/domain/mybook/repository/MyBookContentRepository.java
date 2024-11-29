package com.capstone.storytune.domain.mybook.repository;

import com.capstone.storytune.domain.mybook.domain.MyBook;
import com.capstone.storytune.domain.mybook.domain.MyBookContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyBookContentRepository extends JpaRepository<MyBookContent, Long> {
    Optional<MyBookContent> findByMyBook_IdAndPage(Long myBookId, int page);
    List<MyBookContent> findAllByMyBook_IdAndPageLessThan(Long myBookId, int page);
    Optional<MyBookContent> findByMyBookAndPage(MyBook myBook, int page);
}
