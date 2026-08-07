package com.chapt_gpt_clone.chaptgpt.service;

import com.chapt_gpt_clone.chaptgpt.Exceptions.UserDoesNotExistsException;
import com.chapt_gpt_clone.chaptgpt.dtoMapper.*;
import com.chapt_gpt_clone.chaptgpt.entity.Conversation;
import com.chapt_gpt_clone.chaptgpt.entity.Users;
import com.chapt_gpt_clone.chaptgpt.repository.ConversationRepository;
import com.chapt_gpt_clone.chaptgpt.repository.UserRepository;
import com.chapt_gpt_clone.chaptgpt.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ConversationDTO conversation;
    private final GenAIService genAIService;

    public ConversationResponse createConversation(CreateConversationRequest createConversationRequest, HttpServletRequest httpServletRequest
    ) {
        Users users = jwtService.getUserFromRequest(httpServletRequest);
        Conversation conversation1 = conversation.toConversation(createConversationRequest, users);
        conversation1.setTitle(
                genAIService.generateTitle(conversation1.getSystemPrompt())
        );
        conversationRepository.save(conversation1);
        return new ConversationResponse(conversation1.getId(), conversation1.getTitle(),
                conversation1.getSystemPrompt(), conversation1.getCreatedAt(), conversation1.getUpdatedAt()
        );
    }
    public Conversation createConversationRaw(CreateConversationRequest createConversationRequest, HttpServletRequest httpServletRequest
    ) {
        Users users = jwtService.getUserFromRequest(httpServletRequest);
        Conversation conversation1 = conversation.toConversation(createConversationRequest, users);
        conversation1.setTitle(
                genAIService.generateTitle(conversation1.getSystemPrompt())
        );
        return conversationRepository.save(conversation1);
    }

    public List<ConversationResponse> getAllConversations(HttpServletRequest httpServletRequest) {
        Users users = jwtService.getUserFromRequest(httpServletRequest);
        return conversationRepository.findByUsers_IdOrderByUpdatedAtDesc(users.getId()).stream()
                .map(conversation::toConversationResponse).collect(Collectors.toList());
    }

    public ConversationResponse getConversations(Long id, HttpServletRequest httpServletRequest) {
        Optional<Conversation> conversation1 = conversationRepository.findById(id);
        return conversation1.map(conversation::toConversationResponse).orElse(null);
    }

    public ConversationResponse renameConversation(Long id, RenameConversationRequest renameConversationRequest, HttpServletRequest httpServletRequest) {
        int result = conversationRepository.findByIdAndUpdate(id, renameConversationRequest.title(), renameConversationRequest.isArchived(), renameConversationRequest.isPinned());
        if (result >= 1) {
            return conversation.toConversationResponse(conversationRepository.findById(id).orElseThrow());
        }
        throw new UserDoesNotExistsException("User with "+ id +" does not exists");
    }

    public void deleteConversation(Long id, HttpServletRequest httpServletRequest){
        conversationRepository.deleteById(id);
    }

    public ConversationResponse updateSystemPrompt(Long id, UpdateSystemPromptRequest updateSystemPrompt){
        int result = conversationRepository.findByIdAndUpdateSystemPrompt(id, updateSystemPrompt.systemPrompt());
        if(result>=1){
            return conversation.toConversationResponse(conversationRepository.findById(id).orElseThrow());
        }
        throw new UserDoesNotExistsException("User with "+ id +" does not exists");
    }
}
