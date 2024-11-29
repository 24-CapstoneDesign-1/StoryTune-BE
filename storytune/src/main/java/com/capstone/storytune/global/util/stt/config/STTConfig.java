package com.capstone.storytune.global.util.stt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class STTConfig {

    @Value("${naver.client_id}")
    String CLIENT_ID;

    @Value("${naver.client_secret}")
    String CLIENT_SECRET;

    @Bean(name = "sttRestTemplate")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("X-NCP-APIGW-API-KEY-ID", CLIENT_ID);
            request.getHeaders().add("X-NCP-APIGW-API-KEY", CLIENT_SECRET);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
