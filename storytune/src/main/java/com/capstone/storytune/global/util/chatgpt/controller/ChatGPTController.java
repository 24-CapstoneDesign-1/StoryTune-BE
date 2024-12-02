package com.capstone.storytune.global.util.chatgpt.controller;

import com.capstone.storytune.global.util.chatgpt.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatGPTController {
    private final ChatGPTService chatGPTService;

    public String makeScenario(List<String> info){
        String instruction = "위의 내용을 바탕으로 시나리오 형식으로 만들어줘. 지문, 대사가 적절하게 시나리오 형식으로 표현되면 좋겠어." +
                "코코 : (웃으며) 난 산책이 좋아." +
                "다른 내용은 추가하지 말고, 내가 보내준 내용을 그냥 형식만 바꿔서 주면 돼. 그리고 다른 말은 하지 말고 원하는 내용만 깔끔하게 출력해줘. 마지막에 \n 넣지 말고.";
        return chatGPTService.chat(info, instruction);
    }

    public String makeStory(List<String> info){
        String instruction = "위의 내용을 바탕으로 줄글 형식으로 만들어줘. 동화책을 만드는 중이야. 다른 내용은 추가하지 말고, 내가 보내준 내용을 그냥 형식만 맞춰서 주면 돼. 그리고 다른 말은 하지 말고 원하는 내용만 깔끔하게 출력해줘. 마지막에 \n 넣지 말고.";
        return chatGPTService.chat(info, instruction);
    }

    public String makeInEnglish(String info){
        String instruction = "위의 내용을 영어로 바꿔줘. 다른 말은 하지 말고, 내가 보내준 내용을 번역만 해주면 돼. 마지막에 \n 넣지 말고";
        return chatGPTService.chat(Collections.singletonList(info), instruction);
    }
}
