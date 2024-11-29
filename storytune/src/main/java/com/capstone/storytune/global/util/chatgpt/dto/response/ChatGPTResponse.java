package com.capstone.storytune.global.util.chatgpt.dto.response;

import lombok.Builder;

import java.util.List;


@Builder
public record ChatGPTResponse(
        List<Choice> choices
) {
    public record Choice(int index, Message message){}
    public record Message(String role, String content) {}
}
