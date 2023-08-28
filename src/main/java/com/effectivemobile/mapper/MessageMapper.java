package com.effectivemobile.mapper;

import org.mapstruct.Mapper;
import com.effectivemobile.entity.Message;
import com.effectivemobile.model.response.MessageResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class MessageMapper {

    public MessageResponseDto toResponseDto(Message message) {
        return MessageResponseDto.builder()
                .text(message.getText())
                .messageId(message.getId())
                .firstUserId(message.getFirstUser().getId())
                .secondUserId(message.getSecondUser().getId())
                .build();
    }

    public abstract List<MessageResponseDto> toResponseDtos(List<Message> messages);

}
