package com.example.demo_springboot_api.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<TData, TError> {
  public boolean success;
  public String message;
  public TData data;
  public TError error = null;

  public BaseResponse(boolean success, String message, TData data, TError error) {
    this.success = success;
    this.message = message;
    this.data = data;
    this.error = error;
  }

  public static <TData, TError> BaseResponse<TData, TError> success(String message, TData data) {
    return new BaseResponse<TData, TError>(true, message, data, null);
  }

  public static <TData, TError> BaseResponse<TData, TError> error(String message, TError error) {
    return new BaseResponse<TData, TError>(false, message, null, error);
  }
}
