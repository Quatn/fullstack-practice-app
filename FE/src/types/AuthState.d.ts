import { UserState } from "./UserState";

export type AuthState = {
  userState: UserState | null;
  accessToken: string | null;
  hydrating: boolean;
  refreshingToken: boolean;
};
