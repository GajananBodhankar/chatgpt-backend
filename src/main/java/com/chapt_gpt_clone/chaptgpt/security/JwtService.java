package com.chapt_gpt_clone.chaptgpt.security;

import com.chapt_gpt_clone.chaptgpt.entity.RefreshToken;
import com.chapt_gpt_clone.chaptgpt.entity.Users;
import com.chapt_gpt_clone.chaptgpt.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${spring.application.security.jwt}")
    private String secret;

    @Value("${spring.application.security.expiration}")
    private Long expiration;

    private final RefreshTokenRepository refreshTokenRepository;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Users users) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .subject(users.getEmail())
                .issuedAt(now)
                .claim("userId", users.getId())
                .expiration(expiry)
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = userDetails.getUsername();
        RefreshToken refreshToken = refreshTokenRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        if(refreshToken.getRevoked()){
            throw new RuntimeException("Token has been revoked, please login and generate new token");
        }
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public long getExpiration() {
        return expiration;
    }
}
