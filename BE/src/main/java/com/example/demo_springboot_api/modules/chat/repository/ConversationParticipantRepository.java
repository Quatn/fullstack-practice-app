package com.example.demo_springboot_api.modules.chat.repository;

import com.example.demo_springboot_api.modules.chat.entity.ConversationParticipant;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ConversationParticipantRepository
    extends CrudRepository<ConversationParticipant, Long>,
        QuerydslPredicateExecutor<ConversationParticipant> {}
