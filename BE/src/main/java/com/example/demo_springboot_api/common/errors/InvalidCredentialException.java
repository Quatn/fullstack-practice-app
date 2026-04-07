package com.example.demo_springboot_api.common.errors;

public class InvalidCredentialException extends RuntimeException {

  public InvalidCredentialException(String message) {
    super(message);
  }
}
