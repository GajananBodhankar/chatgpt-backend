package com.chapt_gpt_clone.chaptgpt.service;

import com.chapt_gpt_clone.chaptgpt.controller.auth.EmailVerificationrepository;
import com.chapt_gpt_clone.chaptgpt.entity.EmailVerification;
import com.chapt_gpt_clone.chaptgpt.enums.VerificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationrepository emailVerificationrepository;
    @Value("${spring.mail.username}")
    private String from;

    public void sendOtp(String email, String otp){
        String encoded = passwordEncoder.encode(otp);
        EmailVerification emailVerification = new EmailVerification(email, encoded , VerificationType.SIGNUP, LocalDateTime.now().plusMinutes(5), false);
        emailVerificationrepository.save(emailVerification);
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Verify your email");
        simpleMailMessage.setText("""
                Welcome!
                
                your email verification code is:
                
                %s
                
                This code expires in 5 minutes
                """.formatted(otp));

        javaMailSender.send(simpleMailMessage);

    }

}
