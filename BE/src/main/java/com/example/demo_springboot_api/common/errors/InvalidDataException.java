package com.example.demo_springboot_api.common.errors;

public class InvalidDataException extends RuntimeException {

  public InvalidDataException(String message) {
    super(message);
  }
}
