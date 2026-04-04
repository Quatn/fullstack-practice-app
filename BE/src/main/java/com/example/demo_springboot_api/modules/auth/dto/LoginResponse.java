package com.example.demo_springboot_api.modules.auth.dto;

import com.example.demo_springboot_api.common.dto.ApiResponse;
import com.example.demo_springboot_api.generated.ErrorCode;

public class LoginResponse extends ApiResponse<LoginResponseData> {
  public LoginResponse(boolean success, String message, LoginResponseData data, ErrorCode error) {
    super(success, message, data, error);
  }
}
