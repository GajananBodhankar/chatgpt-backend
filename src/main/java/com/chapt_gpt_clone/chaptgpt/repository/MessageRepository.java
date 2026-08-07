package com.chapt_gpt_clone.chaptgpt.repository;

import com.chapt_gpt_clone.chaptgpt.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Transactional
    @Modifying
    @Query("""
            DELETE FROM Message m
            WHERE m.conversation.id = :conversationId AND
            m.messageId > :messageId
            """)
    int deleteMessagesAfter(@Param("conversationId") Long conversationId, @Param("messageId") Long messageId);
}
