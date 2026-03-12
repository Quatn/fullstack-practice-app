package com.example.demo_springboot_api.common.errors;

public class InvalidAuthSessionException extends RuntimeException {

  public InvalidAuthSessionException(String message) {
    super(message);
  }
}
