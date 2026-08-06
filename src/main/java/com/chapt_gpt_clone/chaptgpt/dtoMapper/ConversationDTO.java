package com.chapt_gpt_clone.chaptgpt.dtoMapper;

import com.chapt_gpt_clone.chaptgpt.entity.Conversation;
import com.chapt_gpt_clone.chaptgpt.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConversationDTO {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "model", source = "request.model")
    @Mapping(target = "systemPrompt", source = "request.systemPrompt")
    @Mapping(target = "users", source = "users")
    @Mapping(target = "isPinned", constant = "false")
    @Mapping(target = "isArchived", constant = "false")
    Conversation toConversation(CreateConversationRequest request, Users users);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "systemPrompt", source = "systemPrompt")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    ConversationResponse toConversationResponse(Conversation conversation);
}