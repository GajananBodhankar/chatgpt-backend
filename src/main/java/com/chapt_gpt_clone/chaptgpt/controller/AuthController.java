package com.chapt_gpt_clone.chaptgpt.controller;

import com.chapt_gpt_clone.chaptgpt.dtoMapper.*;
import com.chapt_gpt_clone.chaptgpt.entity.RefreshToken;
import com.chapt_gpt_clone.chaptgpt.service.AuthService;
import com.chapt_gpt_clone.chaptgpt.service.ConversationService;
import com.chapt_gpt_clone.chaptgpt.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ApiResponse<Object> signup(@Valid @RequestBody SignupRequest loginSignupRequest, HttpServletRequest httpServletRequest) {
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

    @PostMapping("/login")
    public ApiResponse<Object> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        authService.login(loginRequest);
        return ApiResponse.builder()
                .data("Otp sent successfully to the registered email.")
                .error(null)
                .path(httpServletRequest.getRequestURI())
                .message("Otp sent successfully to the registered email.")
                .status(HttpStatus.CREATED.value())
                .build();
    }

    @PostMapping("/verifyOtp")
    public ApiResponse<Object> verifyOtp(@Valid @RequestBody VerifySignupRequest verifySignupRequest, HttpServletRequest httpServletRequest) {
        System.out.println("VerifySignupRequest" + verifySignupRequest.otp() + "dsadsa" + verifySignupRequest.email());
        JwtResponse jwtResponse = authService.verifyEmailOtp(verifySignupRequest);
        return ApiResponse.builder()
                .data(jwtResponse)
                .error(null)
                .path(httpServletRequest.getRequestURI())
                .message("Otp verification successful.")
                .status(HttpStatus.CREATED.value())
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<Object> generateRefreshToken(@RequestBody RefreshRequest refreshRequest, HttpServletRequest httpServletRequest) {
        return ApiResponse.builder()
                .data(refreshTokenService.verify(refreshRequest.refreshToken()))
                .error(null)
                .path(httpServletRequest.getRequestURI())
                .message("Refresh token verified successfully.")
                .status(HttpStatus.CREATED.value())
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Object> logout(HttpServletRequest request) {
        authService.logout(request);
        return ApiResponse.builder()
                .data(null)
                .error(null)
                .status(HttpStatus.NO_CONTENT.value())
                .build();
    }

    @GetMapping("/check")
    public ApiResponse<Object> healthCheck() {
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }
}
