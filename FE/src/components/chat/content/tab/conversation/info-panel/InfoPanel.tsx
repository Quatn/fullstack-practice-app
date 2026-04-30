import { ChatConversationTabReducerStore } from "@/context/chat/conversation-tab";
import { Avatar, Box, Collapsible, Flex, HStack, IconButton, Spacer, Stack, Text } from "@chakra-ui/react";
import { LuSearch } from "react-icons/lu";

export default function ConversationTabContentInfoPanel() {
  const { useSelector: useTabSelector, useDispatch: useTabDispatch } = ChatConversationTabReducerStore;
  const tabDispatch = useTabDispatch();
  const isInfoPanelExpanded = useTabSelector(s => s.isInfoPanelExpanded);

  return (
    <Collapsible.Root open={isInfoPanelExpanded} onOpenChange={(e) => tabDispatch({ type: "SET_EXPAND_INFO_PANEL", payload: e.open })}>
      <Stack>
        <HStack>
          <Avatar.Root>
            <Avatar.Image src="" />
            <Avatar.Fallback name="User" />
          </Avatar.Root>
          <Spacer flexGrow={1} />
          <Collapsible.Trigger paddingY="3">
            <IconButton aria-label="Search database">
              <LuSearch />
            </IconButton>
          </Collapsible.Trigger>
        </HStack>
      </Stack>

      <Collapsible.Content>
        <Box>
          <Text>
            Info about the conversation
          </Text>
        </Box>
      </Collapsible.Content>
    </Collapsible.Root>
  );
}
