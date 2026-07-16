package com.chapt_gpt_clone.chaptgpt.dtoMapper;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifySignupRequest(

        @Email
        @NotBlank
        String email,

        @NotBlank
        String otp
        ){};
