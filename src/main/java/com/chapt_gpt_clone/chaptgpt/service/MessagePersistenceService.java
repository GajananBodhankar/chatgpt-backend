package com.chapt_gpt_clone.chaptgpt.service;

import com.chapt_gpt_clone.chaptgpt.entity.Conversation;
import com.chapt_gpt_clone.chaptgpt.entity.Message;
import com.chapt_gpt_clone.chaptgpt.enums.MessageRole;
import com.chapt_gpt_clone.chaptgpt.enums.MessageStatus;
import com.chapt_gpt_clone.chaptgpt.repository.ConversationRepository;
import com.chapt_gpt_clone.chaptgpt.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessagePersistenceService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;

    @Transactional
    public void saveAssistantMessage(Long conversationId, String content){
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow();
        Message message=new Message(MessageRole.ASSISTANT, MessageStatus.COMPLETE, "", "", content, conversation);
        messageRepository.save(message);
    }
}
