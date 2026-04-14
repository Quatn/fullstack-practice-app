package com.example.demo_springboot_api.modules.auth.dto;

import com.example.demo_springboot_api.common.constants.EmailRegex;
import com.example.demo_springboot_api.common.constants.PasswordRegex;
import com.example.demo_springboot_api.common.constants.UserCodeRegex;
import com.example.demo_springboot_api.config.constants.PasswordConfig;
import com.example.demo_springboot_api.config.constants.UserInfoConfig;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterForm(
    @Size(min = UserInfoConfig.MIN_USER_CODE_LENGTH, max = UserInfoConfig.MAX_USER_CODE_LENGTH)
        @Pattern(regexp = UserCodeRegex.USER_CODE_REGEX, message = "Invalid user code format")
        String code,
    @Pattern(regexp = EmailRegex.EMAIL_REGEX, message = "Must be a valid email") String email,
    @Size(min = UserInfoConfig.MIN_USER_NAME_LENGTH, max = UserInfoConfig.MAX_USER_NAME_LENGTH)
        String name,
    @Size(min = PasswordConfig.MIN_PASSWORD_LENGTH, max = PasswordConfig.MAX_PASSWORD_LENGTH)
        @Pattern(regexp = PasswordRegex.PASSWORD_REGEX, message = "Invalid password format")
        String password) {}
