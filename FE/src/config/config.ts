import passwordConfig from "@/config/password-config.json"
import localeConfig from "@/config/locale-config.json"
import userInfoConfig from "@/config/user-info-config.json"
import webSocketConfig from "@/config/web-socket-config.json"
import { ConfigurationError } from "@/lib/errors/ConfigurationError"
import check from "check-types";

// check-types has one but it wont throw custom errors for some reason
function assert<TData>(assertValidFunc: (value: TData) => boolean, errorMessage: string) {
  return (value: TData) => {
    if (!assertValidFunc(value)) {
      throw new ConfigurationError(`Configuration Error: ${errorMessage}`)
    }
    return true;
  }
}

check.map(localeConfig, {
  LOCALES: assert(
    check.array.of.nonEmptyString,
    "LOCALES must be an array of non-empty strings."),

  DEFAULT_LOCALE: assert(
    (l) => check.in(l, localeConfig.LOCALES),
    "DEFAULT_LOCALE must be a value that's in LOCALES."),
})

check.map(passwordConfig, {
  MIN_PASSWORD_LENGTH: assert(
    (l) => check.inRange(l, 1, passwordConfig.MAX_PASSWORD_LENGTH),
    "MIN_PASSWORD_LENGTH must be between 1 and MAX_PASSWORD_LENGTH."),

  MAX_PASSWORD_LENGTH: assert(
    (l) => check.inRange(l, passwordConfig.MIN_PASSWORD_LENGTH, 72),
    "MIN_PASSWORD_LENGTH must be between MIN_PASSWORD_LENGTH and 72. Due to the algorithm used to hash passwords, MAX_PASSWORD_LENGTH cannot be more than 72."),
})

check.map(userInfoConfig, {
  MIN_USER_CODE_LENGTH: assert(
    (l) => check.inRange(l, 1, userInfoConfig.MAX_USER_CODE_LENGTH),
    "MIN_USER_CODE_LENGTH must be between 1 and MAX_USER_CODE_LENGTH"),

  MIN_USER_NAME_LENGTH: assert(
    (l) => check.inRange(l, 1, userInfoConfig.MAX_USER_NAME_LENGTH),
    "MIN_USER_CODE_LENGTH must be between 1 and MAX_USER_CODE_LENGTH"),
})

check.map(webSocketConfig, {
  WS_HEART_BEAT_IN_COMING: assert(
    check.positive,
    "WS_HEART_BEAT_IN_COMING must positive."),

  WS_HEART_BEAT_OUT_GOING: assert(
    check.positive,
    "WS_HEART_BEAT_OUT_GOING must positive."),

  WS_RECONNECT_DELAY: assert(
    check.positive,
    "WS_RECONNECT_DELAY must positive."),

  WS_APPLICATION_DESTINATION_PREFIX: assert(
    check.string,
    "WS_APPLICATION_DESTINATION_PREFIX must be a string."),

  WS_BROKER_ENDPOINT: assert(
    check.string,
    "WS_BROKER_ENDPOINT must be a string."),

  WS_SOCKET_URL_ENDPOINT: assert(
    check.string,
    "WS_SOCKET_URL_ENDPOINT must be a string."),
})

export const config = {
  ...localeConfig,
  ...passwordConfig,
  ...userInfoConfig,
  ...webSocketConfig,
}
