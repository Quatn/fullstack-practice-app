package com.example.demo_springboot_api.modules.auth.dto;

import com.example.demo_springboot_api.common.constants.PasswordRegex;
import com.example.demo_springboot_api.common.constants.UserCodeRegex;
import com.example.demo_springboot_api.config.constants.PasswordConstants;
import com.example.demo_springboot_api.config.constants.UserInfoConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginForm(
    @NotNull
        @Size(
            min = UserInfoConstants.MIN_USER_CODE_LENGTH,
            max = UserInfoConstants.MAX_USER_CODE_LENGTH)
        @Pattern(regexp = UserCodeRegex.USER_CODE_REGEX, message = "Invalid user code format")
        String loginKey,
    @NotNull
        @Size(
            min = PasswordConstants.MIN_PASSWORD_LENGTH,
            max = PasswordConstants.MAX_PASSWORD_LENGTH)
        @Pattern(regexp = PasswordRegex.PASSWORD_REGEX, message = "Invalid password format")
        String password) {}
