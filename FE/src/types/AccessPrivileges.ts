export enum SystemAccessPrivilege {
  Admin = "system-admin",
  Read = "system-read",
  ReadWrite = "system-readWrite",
}

export type AnyAccessPrivileges =
  | `${SystemAccessPrivilege}`
  ;

export const ALL_ACCESS_PRIVILEGE_VALUES: string[] = [
  ...Object.values(SystemAccessPrivilege),
];

