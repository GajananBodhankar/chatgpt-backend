package com.chapt_gpt_clone.chaptgpt.dtoMapper;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RenameConversationRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 150, message = "Title cannot exceed 150 characters")
        @Column(nullable = false, length = 150)
        String title,
        @NotNull(message = "Pinned status is required")
        @Column(nullable = false)
        Boolean isPinned,
        @NotNull(message = "Archived status is required")
        @Column(nullable = false)
        Boolean isArchived) {
}
