package com.chapt_gpt_clone.chaptgpt.dtoMapper;

import com.chapt_gpt_clone.chaptgpt.enums.MessageRole;
import com.chapt_gpt_clone.chaptgpt.enums.MessageStatus;

public record MessageResponse(Long id, MessageRole messageRole, MessageStatus messageStatus, String content) {
}
