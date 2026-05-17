import { HStack, Text } from "@chakra-ui/react";

export type ChatModuleMarkdownMessageProps = {
  content: string,
  isOnSenderSide: boolean,
}

export default function ChatModuleMarkdownMessage(props: ChatModuleMarkdownMessageProps) {
  const { content } = props;

  return (
    <HStack w="full">
      <Text>{content}</Text>
    </HStack>
  );
}
