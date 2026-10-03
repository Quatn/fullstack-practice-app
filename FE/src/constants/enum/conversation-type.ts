import { LocaleKey } from "@/lib/intl/intl";

export enum ConversationType {
  dm = "dm",
  room = "room",
}

export const ChatSidebarTabLocalKeyMap = (tab: ConversationType): LocaleKey | undefined => {
  switch (tab) {
    default:
      return undefined;
  }
}

export const ChatSidebarTabLinkMap = (tab: ConversationType): string => {
  switch (tab) {
    case ConversationType.dm:
      return "dm";
    case ConversationType.room:
      return "room";
  }
}

