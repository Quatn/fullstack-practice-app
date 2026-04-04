package com.example.demo_springboot_api.common.dto;

import com.example.demo_springboot_api.generated.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<TData> extends BaseResponse<TData, ErrorCode> {
  public ApiResponse(boolean success, String message, TData data, ErrorCode error) {
    super(success, message, data, error);
  }
}
