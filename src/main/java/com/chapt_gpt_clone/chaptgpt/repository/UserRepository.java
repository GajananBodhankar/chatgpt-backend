package com.chapt_gpt_clone.chaptgpt.repository;

import com.chapt_gpt_clone.chaptgpt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("""
            UPDATE Users  u
            SET u.emailVerified = true
            WHERE u.email = :email
            """)
    int markEmailVerified(String email);
}
