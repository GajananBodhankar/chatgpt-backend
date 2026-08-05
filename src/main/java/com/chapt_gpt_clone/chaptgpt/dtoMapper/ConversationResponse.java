package com.chapt_gpt_clone.chaptgpt.dtoMapper;


import java.time.LocalDateTime;

public record ConversationResponse(Long id,String title, String systemPrompt, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
