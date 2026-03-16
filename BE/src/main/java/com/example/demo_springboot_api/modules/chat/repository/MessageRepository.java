package com.example.demo_springboot_api.modules.chat.repository;

import com.example.demo_springboot_api.modules.chat.entity.Message;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository
    extends CrudRepository<Message, Long>, QuerydslPredicateExecutor<Message> {}
