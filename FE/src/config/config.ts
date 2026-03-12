import passwordConfig from "@/config/password-config.json"
import userInfoConfig from "@/config/user-info-config.json"
import { ConfigurationError } from "@/lib/errors/ConfigurationError"
import check from "check-types";

// check-types has one but it wont throw custom errors for some reason
function assert<TData>(func: (value: TData) => boolean, errorMessage: string) {
  return (value: TData) => {
    if (!func(value)) {
      throw new ConfigurationError(`Configuration Error: ${errorMessage}`)
    }
    return true;
  }
}

check.map(passwordConfig, {
  MIN_PASSWORD_LENGTH: assert(
    (l) => check.inRange(l, 1, passwordConfig.MAX_PASSWORD_LENGTH),
    "MIN_PASSWORD_LENGTH must be between 1 and MAX_PASSWORD_LENGTH."),

  MAX_PASSWORD_LENGTH: assert(
    (l) => check.inRange(l, passwordConfig.MIN_PASSWORD_LENGTH, 72),
    "MIN_PASSWORD_LENGTH must be between MIN_PASSWORD_LENGTH and 72. Due to the algorithm used to hash passwords, MAX_PASSWORD_LENGTH cannot be more than 72."),
})

check.all(check.map(userInfoConfig, {
  MIN_USER_CODE_LENGTH: assert(
    (l) => check.inRange(l, 1, userInfoConfig.MAX_USER_CODE_LENGTH),
    "MIN_USER_CODE_LENGTH must be between 1 and MAX_USER_CODE_LENGTH"),
  MIN_USER_NAME_LENGTH: assert(
    (l) => check.inRange(l, 1, userInfoConfig.MAX_USER_NAME_LENGTH),
    "MIN_USER_CODE_LENGTH must be between 1 and MAX_USER_CODE_LENGTH"),
}))

export const config = {
  ...passwordConfig,
  ...userInfoConfig,
}
