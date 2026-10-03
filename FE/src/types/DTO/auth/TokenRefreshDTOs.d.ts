import { UserState } from "@/types/UserState";

export class TokenRefreshRequest { }

export class TokenRefreshResponse {
  userState: UserState;
  accessToken: string;
}
