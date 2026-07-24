package com.chapt_gpt_clone.chaptgpt.Exceptions;


public class OtpAlreadyUsedException extends RuntimeException{

    public OtpAlreadyUsedException(String message){
        super(message);
    }
}
