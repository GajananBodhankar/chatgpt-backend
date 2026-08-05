package com.chapt_gpt_clone.chaptgpt.service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class TitleGeneratorService {

    private final ChatClient chatClient;

    public TitleGeneratorService(ChatClient.Builder chatClientBuilder) {
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

    private String cleanTitle(String rawTitle) {
        if (rawTitle == null) {
            return "New Conversation";
        }
        return rawTitle.trim().replaceAll("^\"|\"$", "");
    }
}
