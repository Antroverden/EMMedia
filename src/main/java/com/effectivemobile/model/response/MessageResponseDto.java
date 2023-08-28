package com.effectivemobile.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponseDto {
    private Long messageId;
    private String text;
    private Long firstUserId;
    private Long secondUserId;
}
