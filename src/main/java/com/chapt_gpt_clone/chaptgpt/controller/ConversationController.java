package com.chapt_gpt_clone.chaptgpt.controller;

import com.chapt_gpt_clone.chaptgpt.dtoMapper.*;
import com.chapt_gpt_clone.chaptgpt.service.ConversationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/conversations")
@RestController
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;


    @PostMapping
    public ApiResponse<ConversationResponse> createConversation(@Valid @RequestBody CreateConversationRequest createConversationRequest, HttpServletRequest httpServletRequest){
        return ApiResponse.<ConversationResponse>builder()
                .data(conversationService.createConversation(createConversationRequest, httpServletRequest))
                .status(HttpStatus.OK.value())
                .error(null)
                .message("Conversation created successfully.")
                .build();
    }

    @GetMapping
    public ApiResponse<List<ConversationResponse>> getAllConversations(HttpServletRequest httpServletRequest){
        return ApiResponse.<List<ConversationResponse>>builder()
                .data(conversationService.getAllConversations(httpServletRequest))
                .status(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ConversationResponse> getConversationId(@PathVariable Long id, HttpServletRequest httpServletRequest){
        return ApiResponse.<ConversationResponse>builder()
                .data(conversationService.getConversations(id, httpServletRequest))
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<ConversationResponse> renameConversation(
            @PathVariable Long id,
            @Valid @RequestBody RenameConversationRequest request,
            HttpServletRequest httpServletRequest) {

        return ApiResponse.<ConversationResponse>builder()
                .data(conversationService.renameConversation(id, request, httpServletRequest))
                .status(HttpStatus.OK.value())
                .message("Conversation renamed successfully.")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteConversation(
            @PathVariable Long id,
            HttpServletRequest httpServletRequest) {

        conversationService.deleteConversation(id, httpServletRequest);

        return ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Conversation deleted successfully.")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ConversationResponse> updateSystemPrompt(@PathVariable Long id,
                                                                @Valid
                                                                @RequestBody UpdateSystemPromptRequest updateSystemPromptRequest){
        return ApiResponse.<ConversationResponse>builder()
                .data(conversationService.updateSystemPrompt(id, updateSystemPromptRequest))
                .status(HttpStatus.OK.value())
                .message("System prompt updated successfully")
                .build();
    }
}
