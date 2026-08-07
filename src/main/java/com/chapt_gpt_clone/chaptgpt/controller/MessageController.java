package com.chapt_gpt_clone.chaptgpt.controller;

import com.chapt_gpt_clone.chaptgpt.dtoMapper.*;
import com.chapt_gpt_clone.chaptgpt.entity.Conversation;
import com.chapt_gpt_clone.chaptgpt.entity.Message;
import com.chapt_gpt_clone.chaptgpt.enums.MessageRole;
import com.chapt_gpt_clone.chaptgpt.enums.MessageStatus;
import com.chapt_gpt_clone.chaptgpt.repository.ConversationRepository;
import com.chapt_gpt_clone.chaptgpt.repository.MessageRepository;
import com.chapt_gpt_clone.chaptgpt.service.ConversationService;
import com.chapt_gpt_clone.chaptgpt.service.GenAIService;
import com.chapt_gpt_clone.chaptgpt.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessageController {


    private final MessageService messageService;
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final GenAIService genAIService;

    @PostMapping(value = "/new", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> createMessage(@Valid @RequestBody CreateConversationRequest request, HttpServletRequest httpServletRequest) {
       return messageService.createMessage(request, httpServletRequest);
    }

    @PostMapping(path = "/{conversationId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> existingMessage(@PathVariable Long conversationId, @Valid @RequestBody MessageRequest messageRequest, HttpServletRequest httpServletRequest) {
       return messageService.createExistingMessage(conversationId, messageRequest, httpServletRequest);
    }

    @PutMapping(value = "/{conversationId}/{messageId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> updateMessage(@PathVariable Long messageId, @PathVariable Long conversationId,@Valid @RequestBody MessageRequest messageRequest){
       return messageService.updateMessage(messageId, conversationId, messageRequest);
    }

}
