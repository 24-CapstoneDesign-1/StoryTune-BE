package com.capstone.storytune.domain.mybook.dto.response;

import com.capstone.storytune.domain.mybook.domain.MyBook;
import lombok.Builder;

@Builder
public record MyBookCreateResponse(
        Long myBookId
) {
    public static MyBookCreateResponse of (MyBook myBook) {
        return MyBookCreateResponse.builder()
                .myBookId(myBook.getId())
                .build();
    }
}
