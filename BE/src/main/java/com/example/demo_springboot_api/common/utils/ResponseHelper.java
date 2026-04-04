package com.example.demo_springboot_api.common.utils;

import com.example.demo_springboot_api.common.dto.BaseResponse;

public class ResponseHelper {
  public static <TData, TError, TResponse extends BaseResponse<TData, TError>> TResponse success(
      String message,
      TData data,
      QuadFunction<Boolean, String, TData, TError, TResponse> constructor) {
    return constructor.apply(true, message, data, null);
  }

  public static <TData, TError, TResponse extends BaseResponse<TData, TError>> TResponse error(
      String message,
      TError error,
      QuadFunction<Boolean, String, TData, TError, TResponse> constructor) {
    return constructor.apply(false, message, null, error);
  }
}
