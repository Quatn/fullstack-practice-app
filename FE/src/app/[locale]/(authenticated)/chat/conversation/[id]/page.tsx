import ConversationTabContentInfoPanel from "@/components/chat/content/tab/conversation/info-panel/InfoPanel";
import ConversationTabContentMessageList from "@/components/chat/content/tab/conversation/MessageList";
import ChatClientLayout from "@/components/chat/layout/chat-client-layout/ChatClientLayout";
import ChatModuleMessageComposeBar from "@/components/chat/ui/MessageComposeBar";
import { ChatConversationTabProvider } from "@/context/chat/conversation-tab";
import { ChatClientLayoutProvider } from "@/context/chat/layout/chat-client-layout";
import { Stack } from "@chakra-ui/react";

export default function Home() {
  return (
    <ChatConversationTabProvider>
      <Stack h="full" bg={"red.50"}>
        <ConversationTabContentInfoPanel />
        <ChatClientLayoutProvider>
          <ChatClientLayout messagePanel={<ConversationTabContentMessageList />} messageComposeBar={<ChatModuleMessageComposeBar />} />
        </ChatClientLayoutProvider>
      </Stack>
    </ChatConversationTabProvider>
  );
}
