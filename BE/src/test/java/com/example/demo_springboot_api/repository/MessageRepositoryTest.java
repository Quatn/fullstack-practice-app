package com.example.demo_springboot_api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.demo_springboot_api.modules.chat.entity.Message;
import com.example.demo_springboot_api.modules.chat.repository.MessageRepository;
import com.example.demo_springboot_api.utils.MockData;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class MessageRepositoryTest extends BaseRepositoryTest {
  @Autowired private MessageRepository messageRepository;

  // @Autowired private TestDataFactory testDataFactory;

  @Test
  void save_shouldSaveConversation() {
    Message message = MockData.mockMessage();

    // Get the repo to add a new record
    message.setId(null);

    Message saved = messageRepository.save(message);

    assertEquals(message, saved);
    assertNotNull(saved.getId());
  }
}
