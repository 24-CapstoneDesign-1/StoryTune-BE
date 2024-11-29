package com.capstone.storytune.global.util.stt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class STTService {
    private static final String url = "https://naveropenapi.apigw.ntruss.com/recog/v1/stt";

    private final ObjectMapper objectMapper = new ObjectMapper(); // json 파싱용

    @Autowired
    @Qualifier("sttRestTemplate")
    private RestTemplate restTemplate;

    public String processStt(MultipartFile file, String lang) {
        try{
            // 바이너리 파일 변환
            byte[] bytes = file.getBytes();

            String urlWithLang = url + "?lang=" + lang;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            HttpEntity<byte[]> requestEntity = new HttpEntity<>(bytes, headers);

            // api 호출
            ResponseEntity<String> response = restTemplate.postForEntity(urlWithLang, requestEntity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                return objectMapper.readTree(responseBody).get("text").asText();

            } else {
                throw new RuntimeException("CSR API 호출 실패: " + response.getStatusCode());
            }
        }catch (IOException e){
            throw new RuntimeException("파일 처리 중 오류 발생 (mp3 형식 필요): " + e.getMessage(), e);
        }
    }
}
