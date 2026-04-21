import { Box, For, HStack, Separator, Stack, Text } from "@chakra-ui/react";
import ToggleExpandButton from "./ToggleExpandButton";
import { ResponsiveSidebarLayoutReducerStore } from "@/context/layout/responsive-sidebar-layout";
import SearchBar from "./SearchBar";
import TabButton, { TabButtonProps } from "./tab/TabButton";
import { LuMessageCircleMore, LuNotebookText, LuPanelLeftClose, LuUserRound } from "react-icons/lu";
import { SidebarTree } from "./tree/Tree";
import SidebarTreeContainer from "./tree/TreeContainer";
import { ChatSidebarTab } from "@/constants/enum/chat-sidebar-tab";

const tabs: TabButtonProps[] = [
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

export default function Sidebar() {
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
        <SidebarTreeContainer>
          <SidebarTree />
        </SidebarTreeContainer>
      </Stack>
    </Box>
  )
}
