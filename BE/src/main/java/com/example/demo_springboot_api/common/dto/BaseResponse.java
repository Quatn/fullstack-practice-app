package com.example.demo_springboot_api.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jspecify.annotations.Nullable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<TData extends Object, TError extends Object> {
  public boolean success;
  public String message;
  @Nullable public TData data;
  @Nullable public TError error;

  public BaseResponse(boolean success, String message, TData data, TError error) {
    this.success = success;
    this.message = message;
    this.data = data;
    this.error = error;
  }
}
