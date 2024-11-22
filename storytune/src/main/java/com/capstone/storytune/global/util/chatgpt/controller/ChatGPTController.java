package com.capstone.storytune.global.util.chatgpt.controller;

import com.capstone.storytune.global.util.chatgpt.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatGPTController {
    private final ChatGPTService chatGPTService;

    public String makeGuide(List<String> info){
        String instruction = "위의 내용을 바탕으로 이 페이지에 아이들이 적합한 이야기를 만들 수 있게, 가이드 질문을 1개 만들어줘. 마지막에 개행 문자 없이 줘.";
        return chatGPTService.chat(info, instruction);
    }

    public String makeScenario(List<String> info){
        String instruction = "위의 내용을 바탕으로 시나리오 형식으로 만들어줘. 지문, 대사가 적절하게 시나리오 형식으로 표현되면 좋겠어.";
        return chatGPTService.chat(info, instruction);
    }

    public String makeStory(List<String> info){
        String instruction = "위의 내용을 바탕으로 줄글 형식으로 만들어줘. 동화책을 만드는 중이야.";
        return chatGPTService.chat(info, instruction);
    }
}
