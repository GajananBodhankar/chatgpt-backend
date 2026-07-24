package com.chapt_gpt_clone.chaptgpt.Exceptions;


public class OtpExpiredException extends RuntimeException {
    public OtpExpiredException(String message) {
        super(message);
    }
}