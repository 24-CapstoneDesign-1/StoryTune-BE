package com.capstone.storytune.domain.mybook.dto.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record ImagesRequest(
        List<MultipartFile> images
) {
}
