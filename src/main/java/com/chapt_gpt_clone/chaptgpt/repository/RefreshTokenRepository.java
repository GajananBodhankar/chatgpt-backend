package com.chapt_gpt_clone.chaptgpt.repository;

import com.chapt_gpt_clone.chaptgpt.entity.RefreshToken;
import com.chapt_gpt_clone.chaptgpt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUsers(Users user);

    @Query("""
            SELECT rt FROM 
            RefreshToken rt
            WHERE rt.users.username = :username
            """)
    Optional<RefreshToken> findByUsername(@Param("username") String username);
}
