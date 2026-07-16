package com.chapt_gpt_clone.chaptgpt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendOtp(String email, String otp){
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
