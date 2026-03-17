package com.example.demo_springboot_api.common.handler;

import com.example.demo_springboot_api.common.dto.BaseResponse;
import com.example.demo_springboot_api.common.errors.DataNotFoundException;
import com.example.demo_springboot_api.common.errors.InvalidDataException;
import com.example.demo_springboot_api.common.errors.InvalidTokenException;
import com.example.demo_springboot_api.common.errors.UserNotFoundException;
import io.jsonwebtoken.MalformedJwtException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<BaseResponse<String, String>> handleUserNotFound(UserNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(BaseResponse.error("User not found", ex.getMessage()));
  }

  @ExceptionHandler(DataNotFoundException.class)
  public ResponseEntity<BaseResponse<String, String>> handleUserNotFound(DataNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(BaseResponse.error("Data not found", ex.getMessage()));
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<BaseResponse<String, String>> handleInvalidToken(InvalidTokenException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(BaseResponse.error("Invalid Token", ex.getMessage()));
  }

  @ExceptionHandler(InvalidDataException.class)
  public ResponseEntity<BaseResponse<String, String>> handleInvalidData(InvalidDataException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(BaseResponse.error("Invalid Data", ex.getMessage()));
  }

  @ExceptionHandler(MalformedJwtException.class)
  public ResponseEntity<BaseResponse<String, String>> handleMalformedJwt(MalformedJwtException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(BaseResponse.error("Malformed Token", ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<BaseResponse<String, Map<String, String>>> handleValidationErrors(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return ResponseEntity.badRequest()
        .body(BaseResponse.error("Method Argument Not Valid", errors));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<BaseResponse<String, String>> handleGeneral(Exception ex) {
    String errors =
        String.join(
            ", ", List.of(ex.getStackTrace()).stream().map((trace) -> trace.toString()).toList());

    // Temporarily returns http 500 errors' messages for development, change before production
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(BaseResponse.error("Internal Server Error: " + ex.getMessage(), errors));
  }
}
