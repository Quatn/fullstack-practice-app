package com.example.demo_springboot_api.modules.chat.controller;

import com.example.demo_springboot_api.common.utils.BindingResultExtractor;
import com.example.demo_springboot_api.common.utils.ResponseHelper;
import com.example.demo_springboot_api.generated.ErrorCode;
import com.example.demo_springboot_api.modules.chat.constant.*;
import com.example.demo_springboot_api.modules.chat.dto.ConversationCreateForm;
import com.example.demo_springboot_api.modules.chat.dto.ConversationCreateResponse;
import com.example.demo_springboot_api.modules.chat.dto.ConversationCreateResponseData;
import com.example.demo_springboot_api.modules.chat.dto.ConversationListParameters;
import com.example.demo_springboot_api.modules.chat.dto.ConversationListResponse;
import com.example.demo_springboot_api.modules.chat.dto.ConversationListResponseData;
import com.example.demo_springboot_api.modules.chat.entity.Conversation;
import com.example.demo_springboot_api.modules.chat.service.ConversationService;
import jakarta.validation.Valid;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(ModuleConstants.BASE_PATH + "/conversation")
public class ConversationController {
  @Autowired ConversationService conversationService;

  @GetMapping(path = "/list")
  public @ResponseBody ResponseEntity<ConversationListResponse> conversationList(
      Pageable pageable, @Valid ConversationListParameters params) {

    return ResponseEntity.status(HttpStatus.OK)
        .body(
            ResponseHelper.success(
                pageable.toString() + params.toString(),
                new ConversationListResponseData(),
                ConversationListResponse::new));
  }

  @PostMapping(path = "/create")
  public @ResponseBody ResponseEntity<ConversationCreateResponse> login(
      @Valid @RequestBody ConversationCreateForm form, BindingResult validationResult) {
    if (validationResult.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(
              ResponseHelper.error(
                  "Unable to create conversation: Validation failed"
                      + BindingResultExtractor.getFieldRejectedValueMessage(validationResult),
                  ErrorCode.COMMON_ERR_METHOD_ARGUMENT_NOT_VALID,
                  ConversationCreateResponse::new));
    }

    Conversation conversation = new Conversation();
    conversation.setId(null);
    conversation.setName(form.name());
    conversation.setCoverUrl(form.coverUrl());
    conversation.setType(form.type());

    long now = System.currentTimeMillis();
    Date currentDate = new Date(now);
    conversation.setCreatedat(currentDate);
    conversation.setUpdatedat(currentDate);

    Conversation result = conversationService.addConversation(conversation);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            ResponseHelper.success(
                "Created new conversation successfully",
                new ConversationCreateResponseData(result),
                ConversationCreateResponse::new));
  }
}
