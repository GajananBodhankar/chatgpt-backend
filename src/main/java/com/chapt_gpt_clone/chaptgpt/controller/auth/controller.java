package com.chapt_gpt_clone.chaptgpt.controller.auth;

import com.chapt_gpt_clone.chaptgpt.dtoMapper.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class controller {
    private final AuthService authService;
    @PostMapping("/auth/signup")
    public ApiResponse<Object> signup(@Valid @RequestBody SignupRequest loginSignupRequest, HttpServletRequest httpServletRequest){
        authService.signup(loginSignupRequest);
        System.out.println("Signup");
        return ApiResponse.builder()
                .data("Otp sent successfully to the registered email.")
                .error(null)
                .path(httpServletRequest.getRequestURI())
                .message("Otp sent successfully to the registered email.")
                .status(HttpStatus.CREATED.value())
                .build();
    }

    @PostMapping("/auth/login")
    public ApiResponse<Object> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest){
        authService.login(loginRequest);
        return ApiResponse.builder()
                .data("Otp sent successfully to the registered email.")
                .error(null)
                .path(httpServletRequest.getRequestURI())
                .message("Otp sent successfully to the registered email.")
                .status(HttpStatus.CREATED.value())
                .build();
    }

    @PostMapping("/auth/verifyOtp")
    public ApiResponse<Object> verifyOtp(@Valid @RequestBody VerifySignupRequest  verifySignupRequest, HttpServletRequest httpServletRequest){
        System.out.println("VerifySignupRequest"+ verifySignupRequest.otp()+ "dsadsa"+verifySignupRequest.email());
       JwtResponse jwtResponse = authService.verifyEmailOtp(verifySignupRequest);
        return ApiResponse.builder()
                .data(jwtResponse)
                .error(null)
                .path(httpServletRequest.getRequestURI())
                .message("Otp verification successful.")
                .status(HttpStatus.CREATED.value())
                .build();
    }

    @GetMapping("/auth/check")
    public ApiResponse<Object> healthCheck(){
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }
}
