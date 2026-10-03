import { UserState } from "@/types/UserState";

export class RegisterRequest {
  code: string;
  email: string;
  name: string;
  password: string;
}

export class RegisterResponse {
  userState: UserState;
}
