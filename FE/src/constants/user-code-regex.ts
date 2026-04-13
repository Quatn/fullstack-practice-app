import { config } from "@/config/config";

// User code must be 3-50 characters (using look ahead), can contain alphanumeric characters (both cases), and the "." character as long as it's surrounded by alphanumeric characters. It must also contain at least 1 alphabet character.
export const USER_CODE_FULL_REGEX = new RegExp(
  `^(?=.{${config.MIN_USER_CODE_LENGTH},${config.MAX_USER_CODE_LENGTH}}$)[a-z0-9]+(?:\\.[a-z0-9]+)*$`
  , "i");

// Only check for character white list.
// For use cases such as form validation, where the validator have different messages based on the validation error, use this instead of the full one.
export const USER_CODE_REGEX = /^[a-z0-9]+(?:\.[a-z0-9]+)*$/i;
