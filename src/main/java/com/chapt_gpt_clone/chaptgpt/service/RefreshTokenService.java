package com.chapt_gpt_clone.chaptgpt.service;

import com.chapt_gpt_clone.chaptgpt.entity.RefreshToken;
import com.chapt_gpt_clone.chaptgpt.entity.Users;
import com.chapt_gpt_clone.chaptgpt.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public String create(Users users) {
        refreshTokenRepository.deleteByUsers(users);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsers(users);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(30));
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    public RefreshToken verify(String token){
        RefreshToken refreshToken=refreshTokenRepository.findByToken(token).orElseThrow(()-> new RuntimeException("Invalid token"));
        if(refreshToken.getRevoked()){
            throw  new RuntimeException("Token has been revoked, please login and generate new token");
        }

        if(refreshToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw  new RuntimeException("Refresh token Expired.");
        }
        return refreshToken;
    }
}
