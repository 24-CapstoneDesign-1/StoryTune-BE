package com.capstone.storytune.domain.user.repository;

import com.capstone.storytune.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
