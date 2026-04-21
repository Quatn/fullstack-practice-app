import { LocaleKey } from "@/lib/intl/intl";

export enum ChatSidebarTab {
  chat = "chat",
  project = "project",
}

export const ChatSidebarTabLocalKeyMap = (tab: ChatSidebarTab): LocaleKey | undefined => {
  switch (tab) {
    default:
      return undefined;
  }
}
