package com.example.demo_springboot_api.common.errors;

public class RevokedAuthSessionException extends RuntimeException {

  public RevokedAuthSessionException(String message) {
    super(message);
  }
}
