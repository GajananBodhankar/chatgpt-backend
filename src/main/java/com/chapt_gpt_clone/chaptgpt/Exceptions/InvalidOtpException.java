package com.chapt_gpt_clone.chaptgpt.Exceptions;

public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException(String message) {
        super(message);
    }
}