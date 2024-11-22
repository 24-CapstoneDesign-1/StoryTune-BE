package com.capstone.storytune.domain.user.dto.response;

import com.capstone.storytune.domain.user.domain.User;
import lombok.Builder;


public record FriendSearchResponse(
        Long userId,
        String name,
        String username
) {
    public static FriendSearchResponse of(User user) {
        return new FriendSearchResponse(
                user.getId(),
                user.getName(),
                user.getUsername()
        );
    }
}
