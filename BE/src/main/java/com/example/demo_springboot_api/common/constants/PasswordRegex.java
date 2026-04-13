package com.example.demo_springboot_api.common.constants;

import com.example.demo_springboot_api.config.constants.UserInfoConfig;

public class PasswordRegex {
  // Password must be 8-200 characters, with at least one lowercase, uppercase, digit, and special
  // character.
  public static final String PASSWORD_FULL_REGEX =
      "^(?=.{%d,%d}$)[a-z0-9]+(?:\\.[a-z0-9]+)*$"
          .formatted(UserInfoConfig.MIN_USER_CODE_LENGTH, UserInfoConfig.MAX_USER_CODE_LENGTH);

  // Only check for at least one lowercase, uppercase, digit, and special character.
  // For use cases such as form validation, where the validator have different messages based on the
  // validation error, use this instead of the full one.
  public static final String PASSWORD_REGEX =
      "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\S]+$";
}
