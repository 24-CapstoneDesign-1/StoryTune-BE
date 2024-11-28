package com.capstone.storytune.domain.roleplaying.dto.request;

import com.capstone.storytune.domain.roleplaying.domain.InviteStatus;

public record ParticipantUpdateRequest(
        InviteStatus status
) {
}
