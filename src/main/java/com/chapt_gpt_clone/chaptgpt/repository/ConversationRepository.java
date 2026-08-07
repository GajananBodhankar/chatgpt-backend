package com.chapt_gpt_clone.chaptgpt.repository;

import com.chapt_gpt_clone.chaptgpt.dtoMapper.RenameConversationRequest;
import com.chapt_gpt_clone.chaptgpt.entity.Conversation;
import org.hibernate.annotations.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByUsers_IdOrderByUpdatedAtDesc(Long userId);

    Optional<Conversation> findByIdAndUsers_Id(Long id, Long userId);

    @Transactional
    @Modifying
    @Query("""
            UPDATE Conversation c
            SET c.title= :title,
            c.isArchived = :isArchived,
            c.isPinned = :isPinned
            WHERE c.id = :id
            """)
    int findByIdAndUpdate(@Param("id") Long id,
                          @Param("title") String title,
                          @Param("isArchived") Boolean isArchived,
                          @Param("isPinned") Boolean isPinned
    );

    @Transactional
    @Modifying
    @Query("""
            UPDATE Conversation c
            SET c.systemPrompt = :systemPrompt
            WHERE c.id = :id
            """)
    int findByIdAndUpdateSystemPrompt(@Param("id") Long id, @Param("systemPrompt") String systemPrompt);

    @Transactional
    @Modifying
    @Query("""
            UPDATE Conversation c
            SET  c.systemPrompt = :systemPrompt,
            c.title= :title
            WHERE c.id= :id
            """)
    int findByIdAndUpdateSystemPromptAndTitle(@Param("id") Long id, @Param("systemPrompt") String systemPrompt,@Param("title") String title );
}
