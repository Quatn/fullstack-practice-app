import { BaseResponse } from "../BaseResponse";

export class LogoutRequest { }

export class LogoutResponse extends BaseResponse<{
  code?: string;
}> { }
