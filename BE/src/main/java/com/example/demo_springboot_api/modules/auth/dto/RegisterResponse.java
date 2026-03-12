package com.example.demo_springboot_api.modules.auth.dto;

import com.example.demo_springboot_api.common.dto.BaseResponse;

record RegisterResponseData(UserState userState) {}

public class RegisterResponse extends BaseResponse<RegisterResponseData, String> {
  public RegisterResponse(
      boolean success, String message, RegisterResponseData data, String error) {
    super(success, message, data, error);
  }

  public static RegisterResponse success(String message, UserState userState) {
    return new RegisterResponse(true, message, new RegisterResponseData(userState), null);
  }

  public static RegisterResponse error(String message, String error) {
    return new RegisterResponse(true, message, null, error);
  }
}
