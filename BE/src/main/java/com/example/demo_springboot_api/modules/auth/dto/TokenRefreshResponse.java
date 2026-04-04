package com.example.demo_springboot_api.modules.auth.dto;

import com.example.demo_springboot_api.common.dto.ApiResponse;
import com.example.demo_springboot_api.generated.ErrorCode;

public class TokenRefreshResponse extends ApiResponse<TokenRefreshResponseData> {
  public TokenRefreshResponse(
      boolean success, String message, TokenRefreshResponseData data, ErrorCode error) {
    super(success, message, data, error);
  }
}
