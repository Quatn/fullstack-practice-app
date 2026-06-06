package com.example.demo_springboot_api.modules.chat.entity;

import com.example.demo_springboot_api.modules.chat.constant.ConversationType;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "conversations")
public class Conversation {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private @Nullable Long id;

  private @Nullable String name;

  private @Nullable String coverUrl;

  @Enumerated(EnumType.STRING)
  private ConversationType type;

  private Date createdAt;

  private Date updatedAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public ConversationType getType() {
    return type;
  }

  public void setType(ConversationType type) {
    this.type = type;
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
