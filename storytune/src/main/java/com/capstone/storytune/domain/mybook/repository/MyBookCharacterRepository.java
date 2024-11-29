package com.capstone.storytune.domain.mybook.repository;

import com.capstone.storytune.domain.mybook.domain.MyBook;
import com.capstone.storytune.domain.mybook.domain.MyBookCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyBookCharacterRepository extends JpaRepository<MyBookCharacter, Long> {
    List<MyBookCharacter> findByMyBook_Id(Long myBookId);
    List<MyBookCharacter> findByMyBook(MyBook myBook);
}
