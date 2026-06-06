package com.example.demo_springboot_api.modules.chat.dto;

import com.example.demo_springboot_api.common.dto.ApiResponse;
import com.example.demo_springboot_api.generated.ErrorCode;

public class ConversationListResponse extends ApiResponse<ConversationListResponseData> {
  public ConversationListResponse(
      boolean success, String message, ConversationListResponseData data, ErrorCode error) {
    super(success, message, data, error);
  }
}
