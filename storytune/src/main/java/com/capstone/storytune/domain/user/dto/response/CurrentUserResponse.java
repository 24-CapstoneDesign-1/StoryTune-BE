package com.capstone.storytune.domain.user.dto.response;

import com.capstone.storytune.domain.user.domain.User;
import lombok.Builder;

@Builder
public record CurrentUserResponse(
        Long userId,
        String name
) {
    public static CurrentUserResponse of(User user) {
        return CurrentUserResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .build();
    }
}
