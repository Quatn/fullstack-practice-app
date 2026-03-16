package com.example.demo_springboot_api.modules.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Date;
import org.jspecify.annotations.Nullable;

@Entity // This tells Hibernate to make a table out of this class
@Table( // This is optional, but recommended for explicitness
    name = "users",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = "code"),
      @UniqueConstraint(columnNames = "email")
    })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private @Nullable Long id;

  private String code;

  private String name;

  private String email;

  private String password;

  private @Nullable String accessPrivileges;

  private Date createdAt;

  private Date updatedAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAccessPrivileges() {
    return accessPrivileges;
  }

  public void setAccessPrivileges(String accessPrivileges) {
    this.accessPrivileges = accessPrivileges;
  }

  public String[] getAccessPrivilegesArray() {
    if (accessPrivileges == null) {
      return new String[0];
    }
    return accessPrivileges.split(",");
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
