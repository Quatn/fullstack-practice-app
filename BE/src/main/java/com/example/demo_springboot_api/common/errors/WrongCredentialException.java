package com.example.demo_springboot_api.common.errors;

public class WrongCredentialException extends RuntimeException {

  public WrongCredentialException(String message) {
    super(message);
  }
}
