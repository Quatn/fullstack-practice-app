package com.example.demo_springboot_api.utils;

import com.example.demo_springboot_api.modules.auth.entity.AuthSession;
import com.example.demo_springboot_api.modules.chat.constant.ConversationType;
import com.example.demo_springboot_api.modules.chat.entity.ChatMessage;
import com.example.demo_springboot_api.modules.chat.entity.Conversation;
import com.example.demo_springboot_api.modules.chat.entity.ConversationParticipant;
import com.example.demo_springboot_api.modules.user.entity.User;
import java.util.Date;

public class MockData {
  public static User mockUser() {
    User user = new User();
    user.setId(Long.valueOf(100));
    user.setCode("big.j.tester");
    user.setName("John");
    user.setEmail("john@gmail.com");
    user.setPassword("Klmnop123@");
    user.setCreatedAt(new Date(1000000));
    user.setUpdatedAt(new Date(1000000));
    return user;
  }

  public static AuthSession mockAuthSession() {
    AuthSession session = new AuthSession();
    User user = mockUser();
    session.setId(Long.valueOf(101));
    session.setUser(user);
    session.setTokenHash("TestTokenHash");
    session.setUUID("TestUUID");
    session.setCreatedAt(new Date(1000000));
    session.setExpiresAt(new Date(1000000));
    session.setRevoked(false);
    session.setDeviceInfo("");
    return session;
  }

  public static Conversation mockConversation() {
    Conversation conversation = new Conversation();
    conversation.setId(Long.valueOf(102));
    conversation.setName("Test Room");
    conversation.setCoverUrl("http://example.com");
    conversation.setType(ConversationType.ROOM);
    conversation.setCreatedat(new Date());
    conversation.setUpdatedat(new Date());
    return conversation;
  }

  public static ConversationParticipant mockConversationParticipant() {
    Conversation conversation = mockConversation();
    User user = mockUser();

    ConversationParticipant conversationParticipant = new ConversationParticipant();
    conversationParticipant.setConversation(conversation);
    conversationParticipant.setUser(user);

    return conversationParticipant;
  }

  public static ChatMessage mockMessage() {
    Conversation conversation = mockConversation();
    User user = mockUser();

    ChatMessage message = new ChatMessage();
    message.setId(Long.valueOf(103));
    message.setConversation(conversation);
    message.setSender(user);
    message.setContent("Test ChatMessage");
    message.setMetaData("test-meta-data=test");
    message.setCreatedat(new Date());
    message.setUpdatedat(new Date());
    return message;
  }
}
