package com.example.demo_springboot_api.common.errors;

public class AuthenticatedOnlyException extends RuntimeException {

  public AuthenticatedOnlyException(String message) {
    super(message);
  }
}
