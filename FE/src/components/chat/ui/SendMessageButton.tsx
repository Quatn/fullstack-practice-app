import { IconButton } from "@chakra-ui/react";
import { LuSendHorizontal } from "react-icons/lu";

export default function ChatModuleSendMessageButton() {
  return (
    <IconButton aria-label="Search database">
      <LuSendHorizontal />
    </IconButton>
  );
}
