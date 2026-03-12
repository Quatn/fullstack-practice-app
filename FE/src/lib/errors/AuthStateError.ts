export class AuthStateError extends Error {
  constructor(message = "AuthState Error") {
    super(message);
    this.name = "AuthStateError";
  }
}
