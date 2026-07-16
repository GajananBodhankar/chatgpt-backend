package com.chapt_gpt_clone.chaptgpt.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class OtpGenerator {

    public String generate(){
        return String.format("%06d", ThreadLocalRandom.current().nextInt(1_000_000));
    }
}
