import { config } from "@/config/config";

// Password must be 8-200 characters, with at least one lowercase, uppercase, digit, and special character.
export const PASSWORD_FULL_REGEX = new RegExp(
  `^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\S]{${config.MIN_PASSWORD_LENGTH},${config.MAX_PASSWORD_LENGTH}}$`
);

// Only check for at least one lowercase, uppercase, digit, and special character.
// For use cases such as form validation, where the validator have different messages based on the validation error, use this instead of the full one.
export const PASSWORD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z\d])[A-Za-z\d\S]+$/;
