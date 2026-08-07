package com.chapt_gpt_clone.chaptgpt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOrigins("http://localhost:3000", "https://myfrontend.com") // Allowed domains
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                .allowedHeaders("*") // Allowed request headers
                .exposedHeaders("Authorization") // Headers exposed to the client
                .allowCredentials(true) // Include cookies or auth headers
                .maxAge(3600); // Cache duration for preflight requests (in seconds)
    }
}
