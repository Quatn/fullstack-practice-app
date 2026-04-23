"use client"

import { ChatSidebarReducerStore } from "@/context/chat/chat-sidebar";
import { Stack } from "@chakra-ui/react";
import ChatClientLayout from "@/components/chat/layout/chat-client-layout/ChatClientLayout";
import ChatModuleMessageComposeBar from "@/components/chat/ui/MessageComposeBar";
import ConversationTabContentMessageList from "./MessageList";
import ConversationTabContentInfoPanel from "./info-panel/InfoPanel";

export default function ConversationTab() {
  const { useSelector: useSidebarSelector, useDispatch: useSidebarDispatch } = ChatSidebarReducerStore;
  const sidebarDispatch = useSidebarDispatch();

  return (
    <Stack h="full" bg={"red.50"}>
      <ConversationTabContentInfoPanel />
      <ChatClientLayout messagePanel={<ConversationTabContentMessageList />} messageComposeBar={<ChatModuleMessageComposeBar />} />
    </Stack>

  );
}
