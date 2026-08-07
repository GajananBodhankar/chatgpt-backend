package com.chapt_gpt_clone.chaptgpt.service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GenAIService {

    private final ChatClient chatClient;

    public GenAIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * Generates a short 4-5 word title summarizing the context/system prompt.
     *
     * @param systemPrompt The system prompt describing the conversation goal
     * @return Generated title (4-5 words max, cleaned of quotes)
     */
    public String generateTitle(String systemPrompt) {
        String instruction = """
                You are a title generator.
                Analyze the provided system prompt context and generate a concise, 
                engaging title summarizing it. 
                STRICT RULE: The title MUST be between 4 and 5 words long. 
                Do not include quotes or extra punctuation.
                """;

        String title = chatClient.prompt()
                .system(instruction)
                .user("System Prompt context: " + systemPrompt)
                .call()
                .content();

        return cleanTitle(title);
    }

    public Flux<String> generateResponseStream(String systemPrompt){

        String instruction = """
                You are ChatGPT, a helpful, knowledgeable, and professional AI assistant.
                
                Your goals are:
                - Provide accurate, clear, and useful answers.
                - Be concise by default, but provide detailed explanations when requested.
                - If the user's request is ambiguous, ask clarifying questions before making assumptions.
                - When explaining technical topics, use examples where helpful.
                - When writing code, produce clean, maintainable, and production-quality code that follows best practices.
                - If you are uncertain about something, clearly state the uncertainty instead of inventing information.
                - Do not fabricate facts, references, APIs, or code behavior.
                - Maintain context throughout the conversation and answer follow-up questions consistently.
                - Format responses using Markdown when it improves readability.
                - Be polite, neutral, and professional.
                
                Always prioritize correctness, clarity, and helpfulness.
                """;
        return chatClient.prompt()
                .system(instruction)
                .user("System Prompt context: " + systemPrompt)
                .stream().content();
    }

    private String cleanTitle(String rawTitle) {
        if (rawTitle == null) {
            return "New Conversation";
        }
        return rawTitle.trim().replaceAll("^\"|\"$", "");
    }
}
