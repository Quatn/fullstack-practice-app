package com.example.demo_springboot_api.modules.auth.dto;

import com.example.demo_springboot_api.common.dto.ApiResponse;
import com.example.demo_springboot_api.generated.ErrorCode;

public class RegisterResponse extends ApiResponse<RegisterResponseData> {
  public RegisterResponse(
      boolean success, String message, RegisterResponseData data, ErrorCode error) {
    super(success, message, data, error);
  }
}
