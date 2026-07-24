package com.chapt_gpt_clone.chaptgpt.config;

import com.chapt_gpt_clone.chaptgpt.dtoMapper.EmailMessage;
import com.chapt_gpt_clone.chaptgpt.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void consume(EmailMessage message) {

        emailService.sendOtp(
                message.getTo(),
                (String) message.getVariables().get("otp")
        );
    }
}