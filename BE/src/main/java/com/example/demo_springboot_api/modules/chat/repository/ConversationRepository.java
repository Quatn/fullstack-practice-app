package com.example.demo_springboot_api.modules.chat.repository;

import com.example.demo_springboot_api.modules.chat.entity.Conversation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ConversationRepository
    extends CrudRepository<Conversation, Long>, QuerydslPredicateExecutor<Conversation> {}
