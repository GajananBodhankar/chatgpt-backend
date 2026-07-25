package com.chapt_gpt_clone.chaptgpt.service;

import com.chapt_gpt_clone.chaptgpt.entity.Users;
import com.chapt_gpt_clone.chaptgpt.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public String create(Users users){
        refreshTokenRepository.deleteByUsers(users);


    }
}
