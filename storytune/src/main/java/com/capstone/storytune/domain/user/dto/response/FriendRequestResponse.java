package com.capstone.storytune.domain.user.dto.response;

import com.capstone.storytune.domain.user.domain.Friend;
import lombok.Builder;

import java.util.List;

@Builder
public record FriendRequestResponse(
        Long friendId,
        String username,
        String name
) {
    public static FriendRequestResponse of(Friend friend){
        return FriendRequestResponse.builder()
                .friendId(friend.getId())
                .username(friend.getFriend().getUsername())
                .name(friend.getFriend().getName())
                .build();
    }
}
