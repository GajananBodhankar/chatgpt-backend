package com.chapt_gpt_clone.chaptgpt.entity;

import com.chapt_gpt_clone.chaptgpt.enums.MessageRole;
import com.chapt_gpt_clone.chaptgpt.enums.MessageStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Message role is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MessageRole messageRole;

    @NotNull(message = "Message status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MessageStatus messageStatus;

    @Size(max = 5000, message = "Metadata cannot exceed 5000 characters")
    @Column(columnDefinition = "TEXT")
    private String metaData;

    @Size(max = 10000, message = "Parts cannot exceed 10000 characters")
    @Column(columnDefinition = "TEXT")
    private String parts;

    @NotBlank(message = "Message content is required")
    @Size(max = 100000, message = "Content cannot exceed 100000 characters")
    @Column(nullable = false)
    @Lob
    private String content;

    @NotNull(message = "Conversation is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

  public Message(MessageRole messageRole, MessageStatus messageStatus,
                           String metaData, String parts, String content, Conversation conversation){
        this.setMessageRole(messageRole);
        this.setMessageStatus(messageStatus);
        this.setContent(content);
        this.setMetaData(metaData);
        this.setConversation(conversation);
    }


}