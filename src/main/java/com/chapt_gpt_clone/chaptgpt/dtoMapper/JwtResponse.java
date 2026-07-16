package com.chapt_gpt_clone.chaptgpt.dtoMapper;

public record JwtResponse(

        String accessToken,

        Long expiresIn
) {
}
