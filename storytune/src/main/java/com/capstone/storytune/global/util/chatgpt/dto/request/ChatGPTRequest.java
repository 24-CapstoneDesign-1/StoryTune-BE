package com.capstone.storytune.global.util.chatgpt.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record ChatGPTRequest(
        String model,
        List<Message> messages,
        Float temperature
) {
    public static ChatGPTRequest of(String model, List<Message> messages, Float temperature) {
        return ChatGPTRequest.builder()
                .model(model)
                .messages(messages)
                .temperature(temperature)
                .build();
    }
}
