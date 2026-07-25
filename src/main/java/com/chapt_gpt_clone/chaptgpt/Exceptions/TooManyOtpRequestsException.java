package com.chapt_gpt_clone.chaptgpt.Exceptions;

public class TooManyOtpRequestsException
        extends RuntimeException {

    public TooManyOtpRequestsException() {
        super("Please wait before requesting another OTP.");
    }

}
