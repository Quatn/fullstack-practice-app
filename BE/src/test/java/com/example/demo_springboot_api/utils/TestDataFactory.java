package com.example.demo_springboot_api.utils;

import com.example.demo_springboot_api.modules.auth.entity.AuthSession;
import com.example.demo_springboot_api.modules.auth.repository.AuthSessionRepository;
import com.example.demo_springboot_api.modules.chat.constant.ConversationType;
import com.example.demo_springboot_api.modules.chat.entity.Conversation;
import com.example.demo_springboot_api.modules.chat.entity.ConversationParticipant;
import com.example.demo_springboot_api.modules.chat.repository.ConversationParticipantRepository;
import com.example.demo_springboot_api.modules.chat.repository.ConversationRepository;
import com.example.demo_springboot_api.modules.user.entity.User;
import com.example.demo_springboot_api.modules.user.repository.UserRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDataFactory {

  @Autowired private UserRepository userRepository;
  @Autowired private AuthSessionRepository authSessionRepository;
  @Autowired private ConversationRepository conversationRepository;
  @Autowired private ConversationParticipantRepository conversationParticipantRepository;

  public User createAndSaveUser(String code) {
    User user = new User();
    user.setCode(code);
    user.setName("Test Name");
    user.setEmail(code + "@test.com");
    user.setPassword("TestPassword123");
    user.setCreatedAt(new Date());
    user.setUpdatedAt(new Date());
    return userRepository.save(user);
  }

  public User createAndSaveUser(User user) {
    user.setId(null);
    return userRepository.save(user);
  }

  public AuthSession createAndSaveSession(User user, String tokenHash, String uuid) {
    long now = System.currentTimeMillis();
    Date createdDate = new Date(now);
    Date expirationDate = new Date(now + 1000 * 90);

    AuthSession session = new AuthSession();
    session.setUser(user);
    session.setTokenHash(tokenHash);
    session.setUUID(uuid);
    session.setCreatedAt(createdDate);
    session.setExpiresAt(expirationDate);
    session.setRevoked(false);

    return authSessionRepository.save(session);
  }

  public Conversation createAndSaveConversation(String name) {
    Conversation conversation = new Conversation();
    conversation.setName(name);
    conversation.setCoverUrl("http://example.com");
    conversation.setType(ConversationType.ROOM);
    conversation.setCreatedat(new Date());
    conversation.setUpdatedat(new Date());
    return conversationRepository.save(conversation);
  }

  public ConversationParticipant createAndSaveConversationParticipant(
      Conversation conversation, User user) {
    ConversationParticipant conversationParticipant = new ConversationParticipant();
    conversationParticipant.setUser(user);
    conversationParticipant.setConversation(conversation);
    conversationParticipant.updateId();

    return conversationParticipantRepository.save(conversationParticipant);
  }
}
