package com.example.demo_springboot_api.common.errors;

public class ExpiredAuthSessionException extends RuntimeException {

  public ExpiredAuthSessionException(String message) {
    super(message);
  }
}
