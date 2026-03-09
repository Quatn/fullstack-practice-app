package com.example.demo_springboot_api.modules.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.jspecify.annotations.Nullable;

@Entity // This tells Hibernate to make a table out of this class
@Table( // This is optional, but recommended for explicitness
    name = "project",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = "code"),
    })
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private @Nullable Integer id;

  private String code;

  private String name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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
}
