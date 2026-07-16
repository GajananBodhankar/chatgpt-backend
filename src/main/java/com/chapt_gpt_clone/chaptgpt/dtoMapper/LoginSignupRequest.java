package com.chapt_gpt_clone.chaptgpt.dtoMapper;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginSignupRequest(

        @Email
        @NotBlank
        String email

) {
}
