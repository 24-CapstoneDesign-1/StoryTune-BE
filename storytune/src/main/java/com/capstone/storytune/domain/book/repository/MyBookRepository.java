package com.capstone.storytune.domain.book.repository;

import com.capstone.storytune.domain.book.domain.MyBook;
import com.capstone.storytune.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {

    // 특정 사용자의 모든 책 ID 목록 가져오기
    @Query("SELECT mb.book.id FROM MyBook mb WHERE mb.user = :user AND mb.book IS NOT NULL")
    List<Long> findBookIdsByUser(@Param("user") User user);

    // 특정 사용자가 소유하지 않은 모든 MyBook 엔티티를 최신 순으로 가져오기
    @Query("SELECT mb FROM MyBook mb WHERE mb.user <> :user AND mb.book IS NOT NULL ORDER BY mb.createdAt DESC")
    List<MyBook> findByUserNotOrderByCreatedDateDesc(@Param("user") User user);

    List<MyBook> findByUserOrderByCreatedAtDesc(User user);
}
