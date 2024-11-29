package com.capstone.storytune.domain.user.dto.response;

import com.capstone.storytune.domain.user.domain.Friend;
import lombok.Builder;

@Builder
public record FriendResponse(
        Long userId,
        String name
) {
    public static FriendResponse of(Friend friend){
        return FriendResponse.builder()
                .userId(friend.getFriend().getId())
                .name(friend.getFriend().getName())
                .build();
    }
}
