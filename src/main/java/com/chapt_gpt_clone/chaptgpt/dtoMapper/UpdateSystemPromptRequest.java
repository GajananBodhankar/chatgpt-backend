package com.chapt_gpt_clone.chaptgpt.dtoMapper;

import jakarta.validation.constraints.Size;
import lombok.Data;


public record UpdateSystemPromptRequest(
        @Size(max = 5000, message = "System prompt cannot exceed 5000 characters")
        String systemPrompt
) {

}