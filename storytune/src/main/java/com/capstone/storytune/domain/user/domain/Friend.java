package com.capstone.storytune.domain.user.domain;

import com.capstone.storytune.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Friend extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friendId")
    private User friend;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    @Builder
    public Friend(User user, User friend) {
        this.user = user;
        this.friend = friend;
        this.status = FriendStatus.PENDING; // 기본 값 -> PENDING
    }

    public void updateStatus(FriendStatus status) {
        this.status = status;
    }
}
