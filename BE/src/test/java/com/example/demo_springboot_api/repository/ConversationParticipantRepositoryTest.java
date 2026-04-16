package com.example.demo_springboot_api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo_springboot_api.modules.chat.entity.Conversation;
import com.example.demo_springboot_api.modules.chat.entity.ConversationParticipant;
import com.example.demo_springboot_api.modules.chat.repository.ConversationParticipantRepository;
import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.utils.TestDataFactory;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class ConversationParticipantRepositoryTest extends BaseRepositoryTest {
  @Autowired private ConversationParticipantRepository conversationParticipantRepository;

  @Autowired private TestDataFactory testDataFactory;

  @Test
  void save_shouldSaveConversationParticipant() {
    User user = testDataFactory.createAndSaveUser("unique.code.1");
    Conversation conversation = testDataFactory.createAndSaveConversation("Room 1");

    ConversationParticipant conversationParticipant = new ConversationParticipant();
    conversationParticipant.setUser(user);
    conversationParticipant.setConversation(conversation);
    conversationParticipant.updateId();

    ConversationParticipant saved = conversationParticipantRepository.save(conversationParticipant);

    assertNotNull(saved.getUser());
    assertNotNull(saved.getConversation());
  }

  @Test
  void findByUserId_shouldReturnConversationParticipant_whenExists() {
    User user = testDataFactory.createAndSaveUser("unique.code.1");
    Conversation conversation = testDataFactory.createAndSaveConversation("Room 1");
    ConversationParticipant conversationParticipant =
        testDataFactory.createAndSaveConversationParticipant(conversation, user);

    Optional<ConversationParticipant> result =
        conversationParticipantRepository.findByUserId(conversationParticipant.getUser().getId());

    assertNotNull(result.orElse(null));
    assertTrue(conversationParticipant.getId().equals(result.get().getId()));
    assertEquals(conversationParticipant.getUser().getId(), result.get().getUser().getId());
  }

  @Test
  void findByConversationId_shouldReturnConversationParticipant_whenExists() {
    User user = testDataFactory.createAndSaveUser("unique.code.1");
    Conversation conversation = testDataFactory.createAndSaveConversation("Room 1");
    ConversationParticipant conversationParticipant =
        testDataFactory.createAndSaveConversationParticipant(conversation, user);

    Optional<ConversationParticipant> result =
        conversationParticipantRepository.findByConversationId(
            conversationParticipant.getConversation().getId());

    assertNotNull(result.orElse(null));
    assertTrue(conversationParticipant.getId().equals(result.get().getId()));
    assertEquals(conversationParticipant.getUser().getId(), result.get().getUser().getId());
  }
}
