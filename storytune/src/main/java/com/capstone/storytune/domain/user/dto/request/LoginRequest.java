package com.capstone.storytune.domain.user.dto.request;


public record LoginRequest(
        String username,
        String password
) {
}
