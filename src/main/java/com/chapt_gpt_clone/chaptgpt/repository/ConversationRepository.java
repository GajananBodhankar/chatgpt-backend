package com.chapt_gpt_clone.chaptgpt.repository;

import com.chapt_gpt_clone.chaptgpt.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByUsers_IdOrderByUpdatedAtDesc(Long userId);
    Optional<Conversation> findByIdAndUsers_Id(Long id, Long userId);
}
