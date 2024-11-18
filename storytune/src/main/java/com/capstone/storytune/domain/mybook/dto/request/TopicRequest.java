package com.capstone.storytune.domain.mybook.dto.request;

import com.capstone.storytune.domain.mybook.domain.Topic;
import lombok.Builder;

@Builder
public record TopicRequest(
        Topic topic
) {
}
