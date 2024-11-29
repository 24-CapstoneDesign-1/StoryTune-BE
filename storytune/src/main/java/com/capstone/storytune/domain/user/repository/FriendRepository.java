package com.capstone.storytune.domain.user.repository;

import com.capstone.storytune.domain.user.domain.Friend;
import com.capstone.storytune.domain.user.domain.FriendStatus;
import com.capstone.storytune.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    boolean existsByUserAndFriend(User user, User friend);
    List<Friend> findAllByFriendAndStatus(User user, FriendStatus status);
    Optional<Friend> findByUserAndFriend(User user, User friend);
    List<Friend> findAllByUserAndStatus(User user, FriendStatus status);

}
