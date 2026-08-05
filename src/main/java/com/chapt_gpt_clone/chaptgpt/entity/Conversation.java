package com.chapt_gpt_clone.chaptgpt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conversation extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Model is required")
    @Size(max = 100, message = "Model name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String model;

    @NotBlank(message = "Title is required")
    @Size(max = 150, message = "Title cannot exceed 150 characters")
    @Column(nullable = false, length = 150)
    private String title;

    @Size(max = 5000, message = "System prompt cannot exceed 5000 characters")
    @Column(columnDefinition = "TEXT")
    private String systemPrompt;

    @NotNull(message = "Pinned status is required")
    @Column(nullable = false)
    private Boolean isPinned = false;

    @NotNull(message = "Archived status is required")
    @Column(nullable = false)
    private Boolean isArchived = false;

    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;
}
