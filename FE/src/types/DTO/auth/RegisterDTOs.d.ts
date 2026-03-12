import { UserState } from "@/types/UserState";
import { BaseResponse } from "../BaseResponse";

export class RegisterRequest {
  code: string;
  email: string;
  name: string;
  password: string;
}

export class RegisterResponse extends BaseResponse<{
  userState: UserState;
}> { }

