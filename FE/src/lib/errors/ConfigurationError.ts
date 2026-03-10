export class ConfigurationError extends Error {
  constructor(message = "Configuration Error") {
    super(message);
    this.name = "ConfigurationError";
  }
}
