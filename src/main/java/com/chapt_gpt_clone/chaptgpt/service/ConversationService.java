package com.chapt_gpt_clone.chaptgpt.service;

import com.chapt_gpt_clone.chaptgpt.dtoMapper.ConversationDTO;
import com.chapt_gpt_clone.chaptgpt.dtoMapper.ConversationResponse;
import com.chapt_gpt_clone.chaptgpt.dtoMapper.CreateConversationRequest;
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
    private final TitleGeneratorService titleGeneratorService;

    public ConversationResponse createConversation(CreateConversationRequest createConversationRequest, HttpServletRequest httpServletRequest
    ) {
        Users users =jwtService.getUserFromRequest(httpServletRequest);
        Conversation conversation1= conversation.toConversation(createConversationRequest, users);
        conversation1.setTitle(
        titleGeneratorService.generateTitle(conversation1.getSystemPrompt())
        );
        conversationRepository.save(conversation1);
        return new ConversationResponse(conversation1.getId(), conversation1.getTitle(),
                conversation1.getSystemPrompt(), conversation1.getCreatedAt(), conversation1.getUpdatedAt()
                );
    }

    public List<ConversationResponse> getAllConversations(HttpServletRequest httpServletRequest){
        Users users =jwtService.getUserFromRequest(httpServletRequest);
       return conversationRepository.findByUsers_IdOrderByUpdatedAtDesc(users.getId()).stream()
               .map(conversation::toConversationResponse).collect(Collectors.toList());
    }

    public ConversationResponse getConversations(Long id, HttpServletRequest httpServletRequest){
        Optional<Conversation> conversation1=conversationRepository.findById(id);
        return conversation1.map(conversation::toConversationResponse).orElse(null);
    }
}
