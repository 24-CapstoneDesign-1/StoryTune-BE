package com.capstone.storytune.domain.user.dto.request;

import com.capstone.storytune.domain.user.domain.Gender;

public record SignupRequest(
        String username,
        String password,
        String name,
        int age,
        Gender gender
) {
}
