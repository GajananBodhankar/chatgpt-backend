package com.chapt_gpt_clone.chaptgpt.dtoMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessage implements Serializable {
    private String to;


    /**
     * Template name.
     * Example:
     * - verification-email
     * - password-reset
     * - welcome-email
     */
    private String template;

    /**
     * Dynamic values to replace inside the template.
     */
    private Map<String, Object> variables;
}