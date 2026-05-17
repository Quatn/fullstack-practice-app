import { HStack, Text } from "@chakra-ui/react";

export type ChatModulePlainTextMessageProps = {
  content: string,
  isOnSenderSide: boolean,
}

export default function ChatModulePlainTextMessage(props: ChatModulePlainTextMessageProps) {
  const { content } = props;

  return (
    <HStack w="full">
      <Text>{content}</Text>
    </HStack>
  );
}
