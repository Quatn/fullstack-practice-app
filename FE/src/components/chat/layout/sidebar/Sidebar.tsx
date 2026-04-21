import { Box, For, HStack, Separator, Stack, Text } from "@chakra-ui/react";
import ToggleExpandButton from "./ToggleExpandButton";
import { ResponsiveSidebarLayoutReducerStore } from "@/context/layout/responsive-sidebar-layout";
import SearchBar from "./SearchBar";
import TabButton, { ChatSidebarTabButtonProps } from "./tab/TabButton";
import { LuMessageCircleMore, LuNotebookText, LuPanelLeftClose, LuUserRound } from "react-icons/lu";
import { ChatSidebarTree } from "./tree/Tree";
import ChatSidebarTreeContainer from "./tree/TreeContainer";
import { ChatSidebarTab } from "@/constants/enum/chat-sidebar-tab";

const tabs: ChatSidebarTabButtonProps[] = [
  {
    icon: LuMessageCircleMore,
    text: "Chat",
    tab: ChatSidebarTab.chat,
  },
  {
    icon: LuNotebookText,
    text: "Project",
    tab: ChatSidebarTab.project,
  },
]

export default function ChatSidebar() {
  const { useSelector, useDispatch } = ResponsiveSidebarLayoutReducerStore;
  const dispatch = useDispatch();
  const isExpanded = useSelector(s => s.isExpanded);

  return (
    <Box bgColor={"bg.panel"} flexGrow={1} h="full" w="full" overflowY={"auto"} p={2}>
      <Stack alignItems={isExpanded ? "flex-start" : "center"}>
        <HStack w="full" justifyContent={isExpanded ? "flex-end" : "center"}>
          <ToggleExpandButton />
        </HStack>
        <Separator w="full" />
        <SearchBar />
        <For
          each={tabs}
        >
          {(item, index) => (
            <TabButton key={index} {...item} />
          )}
        </For>
        <ChatSidebarTreeContainer>
          <ChatSidebarTree />
        </ChatSidebarTreeContainer>
      </Stack>
    </Box>
  )
}
