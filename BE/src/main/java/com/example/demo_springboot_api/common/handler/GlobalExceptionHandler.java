package com.example.demo_springboot_api.common.handler;

import com.example.demo_springboot_api.common.dto.ApiResponse;
import com.example.demo_springboot_api.common.errors.DataNotFoundException;
import com.example.demo_springboot_api.common.errors.InvalidDataException;
import com.example.demo_springboot_api.common.errors.InvalidTokenException;
import com.example.demo_springboot_api.common.errors.UserNotFoundException;
import com.example.demo_springboot_api.common.utils.ResponseHelper;
import com.example.demo_springboot_api.generated.ErrorCode;
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
  public ResponseEntity<ApiResponse<Object>> handleUserNotFound(UserNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            ResponseHelper.error(
                "User not found", ErrorCode.USER_ERR_USER_NOT_FOUND, ApiResponse::new));
  }

  @ExceptionHandler(DataNotFoundException.class)
  public ResponseEntity<ApiResponse<Object>> handleUserNotFound(DataNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            ResponseHelper.error(
                "Data not found", ErrorCode.COMMON_ERR_DATA_NOT_FOUND, ApiResponse::new));
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<ApiResponse<Object>> handleInvalidToken(InvalidTokenException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            ResponseHelper.error(
                "Invalid token", ErrorCode.AUTH_TOKEN_ERR_INVALID_TOKEN, ApiResponse::new));
  }

  @ExceptionHandler(MalformedJwtException.class)
  public ResponseEntity<ApiResponse<Object>> handleMalformedJwt(MalformedJwtException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            ResponseHelper.error(
                "Malformed token", ErrorCode.AUTH_TOKEN_ERR_MALFORMED_TOKEN, ApiResponse::new));
  }

  @ExceptionHandler(InvalidDataException.class)
  public ResponseEntity<ApiResponse<Object>> handleInvalidData(InvalidDataException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            ResponseHelper.error(
                "Invalid data", ErrorCode.COMMON_ERR_INVALID_DATA, ApiResponse::new));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Object>> handleValidationErrors(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return ResponseEntity.badRequest()
        .body(
            ResponseHelper.error(
                "Method Argument Not Valid: " + errors.toString(),
                ErrorCode.COMMON_ERR_METHOD_ARGUMENT_NOT_VALID,
                ApiResponse::new));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Object>> handleGeneral(Exception ex) {
    String errors =
        String.join(
            ", ", List.of(ex.getStackTrace()).stream().map((trace) -> trace.toString()).toList());

    // Temporarily returns http 500 errors' messages for development, change before production
    return ResponseEntity.internalServerError()
        .body(
            ResponseHelper.error(
                errors, ErrorCode.COMMON_ERR_INTERNAL_SERVER_ERROR, ApiResponse::new));
  }
}
