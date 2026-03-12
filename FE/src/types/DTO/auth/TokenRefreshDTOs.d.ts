import { UserState } from "@/types/UserState";
import { BaseResponse } from "../BaseResponse";

export class TokenRefreshRequest { }

export class TokenRefreshResponse extends BaseResponse<{
  userState: UserState;
  accessToken: string;
}> { }

