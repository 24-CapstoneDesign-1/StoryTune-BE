package com.capstone.storytune.domain.user.dto.response;

import com.capstone.storytune.domain.user.domain.User;


public record UserSearchResponse(
        Long userId,
        String name,
        String username
) {
    public static UserSearchResponse of(User user) {
        return new UserSearchResponse(
                user.getId(),
                user.getName(),
                user.getUsername()
        );
    }
}
