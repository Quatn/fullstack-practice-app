package com.example.demo_springboot_api.common.handler;

import com.example.demo_springboot_api.common.dto.ApiResponse;
import com.example.demo_springboot_api.common.errors.AuthenticatedOnlyException;
import com.example.demo_springboot_api.common.errors.DataNotFoundException;
import com.example.demo_springboot_api.common.errors.ExpiredAuthSessionException;
import com.example.demo_springboot_api.common.errors.InvalidAuthSessionException;
import com.example.demo_springboot_api.common.errors.InvalidDataException;
import com.example.demo_springboot_api.common.errors.InvalidTokenException;
import com.example.demo_springboot_api.common.errors.RevokedAuthSessionException;
import com.example.demo_springboot_api.common.errors.UserNotFoundException;
import com.example.demo_springboot_api.common.utils.ResponseHelper;
import com.example.demo_springboot_api.generated.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
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

  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<ApiResponse<Object>> handleExpiredJwtException(ExpiredJwtException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            ResponseHelper.error(
                "Token expired", ErrorCode.AUTH_TOKEN_ERR_EXPIRED_TOKEN, ApiResponse::new));
  }

  @ExceptionHandler(AuthenticatedOnlyException.class)
  public ResponseEntity<ApiResponse<Object>> handleAuthenticatedOnlyException(
      AuthenticatedOnlyException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            ResponseHelper.error(
                "Authentication required",
                ErrorCode.AUTH_STATE_ERR_AUTHENTICATED_ONLY,
                ApiResponse::new));
  }

  @ExceptionHandler(InvalidAuthSessionException.class)
  public ResponseEntity<ApiResponse<Object>> handleInvalidAuthSessionException(
      InvalidAuthSessionException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            ResponseHelper.error(
                "Invalid auth session",
                ErrorCode.AUTH_SESSION_ERR_INVALID_AUTH_SESSION,
                ApiResponse::new));
  }

  @ExceptionHandler(RevokedAuthSessionException.class)
  public ResponseEntity<ApiResponse<Object>> handleRevokedAuthSessionException(
      RevokedAuthSessionException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            ResponseHelper.error(
                "Auth session revoked",
                ErrorCode.AUTH_SESSION_ERR_REVOKED_AUTH_SESSION,
                ApiResponse::new));
  }

  @ExceptionHandler(ExpiredAuthSessionException.class)
  public ResponseEntity<ApiResponse<Object>> handleExpiredAuthSessionException(
      ExpiredAuthSessionException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            ResponseHelper.error(
                "Auth session expired",
                ErrorCode.AUTH_SESSION_ERR_EXPIRED_AUTH_SESSION,
                ApiResponse::new));
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
    // Temporarily returns http 500 errors' messages for development, change before production
    String errors =
        ex.getMessage()
            + " | "
            + String.join(
                ", ",
                List.of(ex.getStackTrace()).stream().map((trace) -> trace.toString()).toList());

    return ResponseEntity.internalServerError()
        .body(
            ResponseHelper.error(
                errors, ErrorCode.COMMON_ERR_INTERNAL_SERVER_ERROR, ApiResponse::new));
  }
}
