package com.chapt_gpt_clone.chaptgpt.dtoMapper;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(@NotBlank String refreshToken) {
}
