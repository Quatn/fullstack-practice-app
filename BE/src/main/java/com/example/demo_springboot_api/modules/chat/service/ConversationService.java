package com.example.demo_springboot_api.modules.chat.service;

import com.example.demo_springboot_api.modules.chat.entity.Conversation;
import com.example.demo_springboot_api.modules.chat.repository.ConversationRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversationService {
  @Autowired private ConversationRepository conversationRepository;

  public List<Conversation> list() {
    List<Conversation> result =
        StreamSupport.stream(conversationRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());

    return result;
  }

  public Conversation addConversation(Conversation conversationInfo) {
    conversationInfo.setId(null);
    return conversationRepository.save(conversationInfo);
  }
}
