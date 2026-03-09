package com.example.demo_springboot_api.common.handler;

import com.example.demo_springboot_api.common.dto.BaseResponse;
import com.example.demo_springboot_api.common.errors.InvalidTokenException;
import com.example.demo_springboot_api.common.errors.UserNotFoundException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<BaseResponse<String, String>> handleUserNotFound(UserNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(BaseResponse.error("User not found", ex.getMessage()));
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<BaseResponse<String, String>> handleInvalidToken(InvalidTokenException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(BaseResponse.error("Invalid Token", ex.getMessage()));
  }

  @ExceptionHandler(MalformedJwtException.class)
  public ResponseEntity<BaseResponse<String, String>> handleMalformedJwt(MalformedJwtException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(BaseResponse.error("Malformed Token", ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<BaseResponse<String, String>> handleGeneral(Exception ex) {
    // Temporarily returns http 500 errors' messages for development, change before production
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(BaseResponse.error("Internal Server Error", ex.getStackTrace().toString()));
  }
}
