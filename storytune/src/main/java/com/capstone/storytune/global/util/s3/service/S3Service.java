package com.capstone.storytune.global.util.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadImage(MultipartFile file) throws IOException {
        // 원래 파일 이름에서 확장자 추출
        String originalFilename = file.getOriginalFilename();
        String extension = extractExtension(originalFilename);

        // 새로운 파일 이름 생성 (UUID + 확장자)
        String fileName = UUID.randomUUID().toString() + "." + extension;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);
        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    private String extractExtension(String filename) {
        // 확장자 추출 로직
        if (filename == null || !filename.contains(".")) {
            throw new IllegalArgumentException("Invalid file name: " + filename);
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
