"use client"

import { ChatSidebarReducerStore } from "@/context/chat/chat-sidebar";
import { Stack } from "@chakra-ui/react";
import ChatClientLayout from "@/components/chat/layout/chat-client-layout/ChatClientLayout";
import ChatModuleMessageComposeBar from "@/components/chat/ui/MessageComposeBar";
import ConversationTabContentMessageList from "./MessageList";
import ConversationTabContentInfoPanel from "./info-panel/InfoPanel";
import { ChatConversationTabProvider, ChatConversationTabReducerStore } from "@/context/chat/conversation-tab";
import { ChatClientLayoutProvider } from "@/context/chat/layout/chat-client-layout";

function Page() {
  const { useSelector: useSidebarSelector, useDispatch: useSidebarDispatch } = ChatSidebarReducerStore;
  const sidebarDispatch = useSidebarDispatch();

  /*
  const { useSelector: useTabSelector, useDispatch: useTabDispatch } = ChatConversationTabReducerStore;
  const tabDispatch = useTabDispatch();
  const isInfoPanelExpanded = useTabSelector(s => s.isInfoPanelExpanded);
  */

  return (
    <Stack h="full" bg={"red.50"}>
      <ConversationTabContentInfoPanel />
      <ChatClientLayoutProvider>
        <ChatClientLayout messagePanel={<ConversationTabContentMessageList />} messageComposeBar={<ChatModuleMessageComposeBar />} />
      </ChatClientLayoutProvider>
    </Stack>
  );
}

export default function ConversationTab() {
  return (
    <ChatConversationTabProvider>
      <Page />
    </ChatConversationTabProvider>
  )
}
