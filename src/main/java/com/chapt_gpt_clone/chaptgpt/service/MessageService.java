package com.chapt_gpt_clone.chaptgpt.service;

import com.chapt_gpt_clone.chaptgpt.dtoMapper.CreateConversationRequest;
import com.chapt_gpt_clone.chaptgpt.dtoMapper.MessageRequest;
import com.chapt_gpt_clone.chaptgpt.entity.Conversation;
import com.chapt_gpt_clone.chaptgpt.entity.Message;
import com.chapt_gpt_clone.chaptgpt.enums.MessageRole;
import com.chapt_gpt_clone.chaptgpt.enums.MessageStatus;
import com.chapt_gpt_clone.chaptgpt.repository.ConversationRepository;
import com.chapt_gpt_clone.chaptgpt.repository.MessageRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final GenAIService genAIService;
    private final ConversationService conversationService;
    private final ConversationRepository conversationRepository;
    private final MessagePersistenceService messagePersistenceService;

    public Flux<String> generateResponse(String systemPrompt) {
        return genAIService.generateResponseStream(systemPrompt);
    }

    @Transactional
    public Flux<String> createMessage(CreateConversationRequest request, HttpServletRequest httpServletRequest) {
        CreateConversationRequest createConversationRequest = new CreateConversationRequest(request.model(), request.systemPrompt());
        Conversation conversation = conversationService.createConversationRaw(createConversationRequest, httpServletRequest);
        Message message = new Message(MessageRole.USER, MessageStatus.COMPLETE, "", "", request.systemPrompt(), conversation);
        messageRepository.save(message);
        StringBuilder assistantContent = new StringBuilder();
        return generateResponse(request.systemPrompt()).doOnNext(assistantContent::append)
                .doOnComplete(() ->
                        {
                            Message message1 = new Message(MessageRole.ASSISTANT, MessageStatus.COMPLETE, "", "", assistantContent.toString(), conversation);
                            messageRepository.save(message1);
                        }
                );
    }

    @Transactional
    public Flux<String> createExistingMessage(Long conversationId, MessageRequest messageRequest, HttpServletRequest httpServletRequest) {
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow();
        Message message = new Message(MessageRole.USER, MessageStatus.COMPLETE, "", "", messageRequest.systemPrompt(), conversation);
        messageRepository.save(message);
        StringBuilder stringBuilder = new StringBuilder();
        return generateResponse(messageRequest.systemPrompt()).doOnNext(stringBuilder::append)
                .doOnComplete(() -> {
                    Message message1 = new Message(MessageRole.ASSISTANT, MessageStatus.COMPLETE, "", "", stringBuilder.toString(), conversation);
                });
    }

    @Transactional
    public Flux<String> updateMessage(Long messageId, Long conversationId, MessageRequest messageRequest) {
        Conversation conversation = updateConversationAndMessage(conversationId, messageId, messageRequest);
        StringBuilder stringBuilder = new StringBuilder();
        return generateResponse(messageRequest.systemPrompt()).doOnNext(stringBuilder::append)
                .doOnComplete(() -> {
                    messagePersistenceService.saveAssistantMessage(conversationId, stringBuilder.toString());
                });
    }

    @Transactional
    protected Conversation updateConversationAndMessage(Long conversationId, Long messageId, MessageRequest messageRequest) {
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow();
        Message message = messageRepository.findById(messageId).orElseThrow();
        if (conversation.getMessageList().getFirst().getId().equals(messageId)) {
            String title = genAIService.generateTitle(messageRequest.systemPrompt());
            conversation.setTitle(title);
            conversation.setSystemPrompt(messageRequest.systemPrompt());
        }
        message.setContent(messageRequest.systemPrompt());
        messageRepository.deleteMessagesAfter(conversationId, messageId);
        return conversation;
    }
}
