import { LocaleKey } from "@/lib/intl/intl";

export enum ChatSidebarTab {
  conversation = "conversation",
  project = "project",
}

export const ChatSidebarTabLocalKeyMap = (tab: ChatSidebarTab): LocaleKey | undefined => {
  switch (tab) {
    default:
      return undefined;
  }
}

export const ChatSidebarTabLinkMap = (tab: ChatSidebarTab): string => {
  switch (tab) {
    case ChatSidebarTab.conversation:
      return "conversation";
    case ChatSidebarTab.project:
      return "project";
  }
}

