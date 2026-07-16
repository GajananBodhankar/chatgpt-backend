package com.chapt_gpt_clone.chaptgpt.controller.auth;

import com.chapt_gpt_clone.chaptgpt.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface emailVerificationrepository extends JpaRepository<EmailVerification, Long> {

    Optional<EmailVerification> findTopByEmailAndOtpAndUsedFalseOrderByCreatedAtDesc(String email, String otp);

    void deleteByEmail(String email);
}
