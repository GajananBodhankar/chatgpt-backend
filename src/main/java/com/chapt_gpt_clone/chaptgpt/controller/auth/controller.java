package com.chapt_gpt_clone.chaptgpt.controller.auth;

import com.chapt_gpt_clone.chaptgpt.dtoMapper.*;
import com.chapt_gpt_clone.chaptgpt.entity.RefreshToken;
import com.chapt_gpt_clone.chaptgpt.service.ConversationService;
import com.chapt_gpt_clone.chaptgpt.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class controller {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final ConversationService conversationService;

    @PostMapping("/auth/signup")
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

    @PostMapping("/auth/login")
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

    @PostMapping("/auth/verifyOtp")
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

    @PostMapping("/auth/refresh")
    public ApiResponse<RefreshToken> generateRefreshToken(@RequestBody RefreshRequest refreshRequest, HttpServletRequest httpServletRequest) {
        return ApiResponse.<RefreshToken>builder()
                .data(refreshTokenService.verify(refreshRequest.refreshToken()))
                .error(null)
                .path(httpServletRequest.getRequestURI())
                .message("Refresh token verified successfully.")
                .status(HttpStatus.CREATED.value())
                .build();
    }

    @PostMapping("/auth/logout")
    public ApiResponse<Object> logout(HttpServletRequest request) {
        authService.logout(request);
        return ApiResponse.builder()
                .data(null)
                .error(null)
                .status(HttpStatus.NO_CONTENT.value())
                .build();
    }

    @PostMapping("/conversations")
    public ApiResponse<ConversationResponse> createConversation(@Valid @RequestBody CreateConversationRequest createConversationRequest, HttpServletRequest httpServletRequest){
        return ApiResponse.<ConversationResponse>builder()
                .data(conversationService.createConversation(createConversationRequest, httpServletRequest))
                .status(HttpStatus.OK.value())
                .error(null)
                .message("Conversation created successfully.")
                .build();
    }

    @GetMapping("/conversations")
    public ApiResponse<List<ConversationResponse>> getAllConversations(HttpServletRequest httpServletRequest){
        return ApiResponse.<List<ConversationResponse>>builder()
                .data(conversationService.getAllConversations(httpServletRequest))
                .status(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("/conversations/{id}")
    public ApiResponse<ConversationResponse> getConversationId(@PathVariable Long id,  HttpServletRequest httpServletRequest){
        return ApiResponse.<ConversationResponse>builder()
                .data(conversationService.getConversations(id, httpServletRequest))
                .build();
    }


    @GetMapping("/auth/check")
    public ApiResponse<Object> healthCheck() {
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }
}
