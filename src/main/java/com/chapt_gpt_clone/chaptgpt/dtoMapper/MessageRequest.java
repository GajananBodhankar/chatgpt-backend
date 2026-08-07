package com.chapt_gpt_clone.chaptgpt.dtoMapper;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

public record MessageRequest(
        @Size(max = 5000, message = "System prompt cannot exceed 5000 characters")
        @Column(columnDefinition = "TEXT")
        String systemPrompt) {
}
