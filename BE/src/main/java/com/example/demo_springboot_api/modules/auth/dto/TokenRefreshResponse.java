package com.example.demo_springboot_api.modules.auth.dto;

import com.example.demo_springboot_api.common.dto.BaseResponse;

record TokenRefreshResponseData(UserState userState, String accessToken) {}

public class TokenRefreshResponse extends BaseResponse<TokenRefreshResponseData, String> {
  public TokenRefreshResponse(
      boolean success, String message, TokenRefreshResponseData data, String error) {
    super(success, message, data, error);
  }

  public static TokenRefreshResponse success(
      String message, UserState userState, String accessToken) {
    return new TokenRefreshResponse(
        true, message, new TokenRefreshResponseData(userState, accessToken), null);
  }

  public static TokenRefreshResponse error(String message, String error) {
    return new TokenRefreshResponse(true, message, null, error);
  }
}
