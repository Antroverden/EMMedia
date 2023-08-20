package com.effectivemobile.mapper;

import org.mapstruct.Mapper;
import com.effectivemobile.entity.Message;
import com.effectivemobile.model.response.MessageResponseDto;

@Mapper(componentModel = "spring")
public abstract class MessageMapper {

    public MessageResponseDto toResponseDto(Message message) {
        return MessageResponseDto.builder()
                .messageId(message.getId())
                .firstUserId(message.getFirstUser().getId())
                .secondUserId(message.getSecondUser().getId())
                .build();
    }
}
