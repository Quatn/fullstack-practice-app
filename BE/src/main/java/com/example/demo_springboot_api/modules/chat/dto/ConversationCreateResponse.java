package com.example.demo_springboot_api.modules.chat.dto;

import com.example.demo_springboot_api.common.dto.ApiResponse;
import com.example.demo_springboot_api.generated.ErrorCode;

public class ConversationCreateResponse extends ApiResponse<ConversationCreateResponseData> {
  public ConversationCreateResponse(
      boolean success, String message, ConversationCreateResponseData data, ErrorCode error) {
    super(success, message, data, error);
  }
}
