package com.capstone.storytune.domain.mybook.repository;

import com.capstone.storytune.domain.mybook.domain.MyBookCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyBookCharacterRepository extends JpaRepository<MyBookCharacter, Long> {
}
