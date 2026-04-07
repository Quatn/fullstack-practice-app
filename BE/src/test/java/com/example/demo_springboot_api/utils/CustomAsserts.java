package com.example.demo_springboot_api.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo_springboot_api.modules.user.entity.User;

public class CustomAsserts {
  public static void assertUserEquals(User u1, User u2) {
    assertEquals(u1.getId(), u2.getId());
    assertEquals(u1.getCode(), u2.getCode());
    assertEquals(u1.getName(), u2.getName());
    assertEquals(u1.getEmail(), u2.getEmail());
    assertEquals(u1.getPassword(), u2.getPassword());
    assertEquals(u1.getCreatedAt(), u2.getCreatedAt());
    assertEquals(u1.getUpdatedAt(), u2.getUpdatedAt());
  }
}
