import { UserState } from "@/types/UserState";

export class LoginRequest {
  loginKey: string;
  password: string;
}

export class LoginResponse {
  userState: UserState;
}
