package com.example.demo_springboot_api.modules.chat.entity;

import com.example.demo_springboot_api.modules.chat.entity.embeddable.ConversationParticipantId;
import com.example.demo_springboot_api.modules.user.entity.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "conversation_participants",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"conversation_id", "user_id"})})
public class ConversationParticipant {

  @EmbeddedId private ConversationParticipantId id;

  @ManyToOne
  @MapsId("conversationId")
  @JoinColumn(name = "conversation_id", nullable = false)
  private Conversation conversation;

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public void setConversation(Conversation conversation) {
    this.conversation = conversation;
  }

  public Conversation getConversation() {
    return conversation;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }
}
