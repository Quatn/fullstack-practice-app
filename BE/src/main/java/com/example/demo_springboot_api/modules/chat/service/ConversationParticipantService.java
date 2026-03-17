package com.example.demo_springboot_api.modules.chat.service;

import com.example.demo_springboot_api.common.errors.DataNotFoundException;
import com.example.demo_springboot_api.modules.chat.entity.ConversationParticipant;
import com.example.demo_springboot_api.modules.chat.repository.ConversationParticipantRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversationParticipantService {
  @Autowired private ConversationParticipantRepository conversationParticipantRepository;

  public ConversationParticipant findByUserId(Long userId) {
    Optional<ConversationParticipant> queryResult =
        conversationParticipantRepository.findByUserId(userId);

    if (queryResult.isEmpty()) {
      throw new DataNotFoundException(
          "ConversationParticipant with this userId was not found: " + userId);
    }

    return queryResult.get();
  }

  public ConversationParticipant findByConversationId(Long conversationId) {
    Optional<ConversationParticipant> queryResult =
        conversationParticipantRepository.findByConversationId(conversationId);

    if (queryResult.isEmpty()) {
      throw new DataNotFoundException(
          "ConversationParticipant with this conversationId was not found: " + conversationId);
    }

    return queryResult.get();
  }
}
