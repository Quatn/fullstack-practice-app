package com.example.demo_springboot_api.common.constants;

import com.example.demo_springboot_api.config.constants.UserInfoConstants;

public class UserCodeRegex {
  // User code must be 3-50 characters (using look ahead), can contain alphanumeric characters (both
  // cases), and the "." character as long as it's surrounded by alphanumeric characters. It must
  // also contain at least 1 alphabet character.
  public static final String USER_CODE_FULL_REGEX =
      "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\S]{%d,%d}$"
          .formatted(
              UserInfoConstants.MIN_USER_CODE_LENGTH, UserInfoConstants.MAX_USER_CODE_LENGTH);

  // Only check for character white list.
  // For use cases such as form validation, where the validator have different messages based on the
  // validation error, use this instead of the full one.
  public static final String USER_CODE_REGEX = "^[a-z0-9]+(?:\\.[a-z0-9]+)*$";
}
