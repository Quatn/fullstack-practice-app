package com.example.demo_springboot_api.modules.auth.dto;

import com.example.demo_springboot_api.common.dto.BaseResponse;

record LoginResponseData(UserState userState) {}

public class LoginResponse extends BaseResponse<LoginResponseData, String> {
  public LoginResponse(boolean success, String message, LoginResponseData data, String error) {
    super(success, message, data, error);
  }

  public static LoginResponse success(String message, UserState userState) {
    return new LoginResponse(true, message, new LoginResponseData(userState), null);
  }

  public static LoginResponse error(String message, String error) {
    return new LoginResponse(true, message, null, error);
  }
}
