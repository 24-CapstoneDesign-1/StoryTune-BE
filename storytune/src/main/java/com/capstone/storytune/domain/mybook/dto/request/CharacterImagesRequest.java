package com.capstone.storytune.domain.mybook.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CharacterImagesRequest(
        List<MultipartFile> images
) {
}
