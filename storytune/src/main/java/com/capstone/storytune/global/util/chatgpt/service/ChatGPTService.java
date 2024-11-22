package com.capstone.storytune.global.util.chatgpt.service;

import com.capstone.storytune.global.util.chatgpt.dto.request.ChatGPTRequest;
import com.capstone.storytune.global.util.chatgpt.dto.request.Message;
import com.capstone.storytune.global.util.chatgpt.dto.response.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatGPTService {
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    private List<Message> makeMessage(List<String> contents, String instruction){
        List<Message> messages = new ArrayList<>();

        messages.add(new Message("system", "너는 초등학생이 나만의 이야기를 만드는 것을 도와주는 독서 교육 선생님이야"));

        for(String content : contents){
            messages.add(new Message("user", content));
        }
        messages.add(new Message("user", instruction));
        return messages;
    }

    public String chat(List<String> contents, String instruction){
        List<Message> messages = makeMessage(contents, instruction);

        ChatGPTRequest chatGPTRequest = ChatGPTRequest.of(model, messages, 0.1f);

        ChatGPTResponse chatGPTResponse = restTemplate.postForObject(url, chatGPTRequest, ChatGPTResponse.class);
        if(chatGPTResponse != null && !chatGPTResponse.choices().isEmpty()){
            return chatGPTResponse.choices().get(0).message().content();
        }
        return null;
    }
}
