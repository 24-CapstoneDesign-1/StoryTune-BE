package com.capstone.storytune.domain.user.dto.request;

import com.capstone.storytune.domain.user.domain.FriendStatus;

public record FriendUpdateRequest(
        FriendStatus status
) {
}
