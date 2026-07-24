package com.chapt_gpt_clone.chaptgpt.dtoMapper;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank(message = "First name cannot be blank")
        @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
        String first_name,

        @NotBlank(message = "Last name cannot be blank")
        @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
        String last_name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Please enter a valid email address")
        @Column(unique = true, nullable = false)
        String email
) {
}
