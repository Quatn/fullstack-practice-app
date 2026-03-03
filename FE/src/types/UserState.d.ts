import { AnyAccessPrivileges } from "./AccessPrivileges";

export interface UserState {
  id: string;
  code: string;
  name: string;
  address: string | null;
  email: string | null;
  accessPrivileges: AnyAccessPrivileges[];
}
