package com.example.demo_springboot_api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.demo_springboot_api.modules.chat.entity.Conversation;
import com.example.demo_springboot_api.modules.chat.repository.ConversationRepository;
import com.example.demo_springboot_api.utils.MockData;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class ConversationRepositoryTest extends BaseRepositoryTest {
  @Autowired private ConversationRepository conversationRepository;

  // @Autowired private TestDataFactory testDataFactory;

  @Test
  void save_shouldSaveConversation() {
    Conversation conversation = MockData.mockConversation();

    // Get the repo to add a new record
    conversation.setId(null);

    Conversation saved = conversationRepository.save(conversation);

    assertEquals(conversation, saved);
    assertNotNull(saved.getId());
  }
}
