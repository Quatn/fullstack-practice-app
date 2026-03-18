package com.example.demo_springboot_api.common.encoder.token;

public interface TokenEncoder {
  public String encode(String input);

  public boolean matches(String rawInput, String storedHash);
}
