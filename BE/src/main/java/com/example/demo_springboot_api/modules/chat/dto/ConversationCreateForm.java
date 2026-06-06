package com.example.demo_springboot_api.modules.chat.dto;

import com.example.demo_springboot_api.config.constants.ConversationInfoConstants;
import com.example.demo_springboot_api.modules.chat.constant.ConversationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ConversationCreateForm(
    @Size(
            min = ConversationInfoConstants.MIN_CONVERSATION_NAME_LENGTH,
            max = ConversationInfoConstants.MAX_CONVERSATION_NAME_LENGTH)
        String name,
    @Size(
            min = ConversationInfoConstants.MIN_CONVERSATION_CONVER_URL_LENGTH,
            max = ConversationInfoConstants.MAX_CONVERSATION_CONVER_URL_LENGTH)
        String coverUrl,
    @NotNull ConversationType type) {}
