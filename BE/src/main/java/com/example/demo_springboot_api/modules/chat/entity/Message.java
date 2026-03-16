package com.example.demo_springboot_api.modules.chat.entity;

import com.example.demo_springboot_api.modules.user.entity.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private @Nullable Long id;

  @ManyToOne
  @JoinColumn(name = "conversation_id", nullable = false)
  private Conversation conversation;

  @ManyToOne
  @JoinColumn(name = "sender_id", nullable = false)
  private User sender;

  private String content;

  private String metaData;

  private Date createdAt;

  private Date updatedAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setConversation(Conversation conversation) {
    this.conversation = conversation;
  }

  public Conversation getConversation() {
    return conversation;
  }

  public void setSender(User sender) {
    this.sender = sender;
  }

  public User getSender() {
    return sender;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getMetaData() {
    return metaData;
  }

  public void setMetaData(String metaData) {
    this.metaData = metaData;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedat(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedat() {
    return updatedAt;
  }

  public void setUpdatedat(Date updatedAt) {
    this.updatedAt = updatedAt;
  }
}
