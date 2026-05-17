import { HStack } from "@chakra-ui/react";
import ChatModuleMessageInput from "./MessageInput";
import ChatModuleSendMessageButton from "./SendMessageButton";
import ChatModuleActionPanel from "./action-panel/ActionPanel";

export default function ChatModuleMessageComposeBar() {
  return (
    <HStack p={2}>
      <ChatModuleActionPanel />
      <ChatModuleMessageInput />
      <ChatModuleSendMessageButton />
    </HStack>
  );
}
