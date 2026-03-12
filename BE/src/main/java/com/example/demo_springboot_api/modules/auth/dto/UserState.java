package com.example.demo_springboot_api.modules.auth.dto;

import com.example.demo_springboot_api.modules.user.entity.User;

public record UserState(
    String id, String code, String name, String email, String[] accessPrivileges) {
  public UserState(User user) {
    this(
        user.getId() + "",
        user.getCode(),
        user.getName(),
        user.getEmail(),
        user.getAccessPrivilegesArray());
  }
}
