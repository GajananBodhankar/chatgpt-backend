package com.chapt_gpt_clone.chaptgpt.dtoMapper;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateConversationRequest(
        @NotBlank(message = "Model is required")
        @Size(max = 100, message = "Model name cannot exceed 100 characters")
        String model,
        @Size(max = 5000, message = "System prompt cannot exceed 5000 characters")
        String systemPrompt
) {}
