package com.chapt_gpt_clone.chaptgpt.dtoMapper;


import com.chapt_gpt_clone.chaptgpt.entity.Conversation;
import com.chapt_gpt_clone.chaptgpt.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageResponseDTO {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "messageRole", source = "message.messageRole")
    @Mapping(target = "messageStatus", source = "message.messageStatus")
    @Mapping(target = "content", constant = "message.content")
    MessageResponse toMessageResponse(Message message);
}
