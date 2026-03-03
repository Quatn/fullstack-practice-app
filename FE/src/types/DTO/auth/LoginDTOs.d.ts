import { UserState } from "@/types/UserState";
import { BaseResponse } from "../BaseResponse";

export class LoginRequest {
  code: string;
  password: string;
}

export class LoginResponse extends BaseResponse<{
  userState: UserState;
}> { }

