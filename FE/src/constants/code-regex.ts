// Password must be 3-50 characters (using look ahead), can contain alphanumeric characters (both cases), and the "." character as long as it's surrounded by alphanumeric characters. It must also contain at least 1 alphabet character.
export const CODE_FULL_REGEX = /^(?=.{3,50}$)[a-z0-9]+(?:\.[a-z0-9]+)*$/i;

// Only check for character white list.
export const CODE_REGEX = /^[a-z0-9]+(?:\.[a-z0-9]+)*$/i;

