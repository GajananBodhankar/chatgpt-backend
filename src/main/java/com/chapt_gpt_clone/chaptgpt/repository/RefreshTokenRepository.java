package com.chapt_gpt_clone.chaptgpt.repository;

import com.chapt_gpt_clone.chaptgpt.entity.RefreshToken;
import com.chapt_gpt_clone.chaptgpt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUsers(Users user);

    @Query("""
            SELECT rt FROM\s
            RefreshToken rt
            WHERE rt.users.email = :email
           \s""")
    Optional<RefreshToken> findByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("""
    UPDATE RefreshToken rt
    SET rt.revoked = :revoked
    WHERE rt.users.email = :email
    """)
    int revokeByEmail(@Param("email") String email, @Param("revoked") Boolean revoke);
}
