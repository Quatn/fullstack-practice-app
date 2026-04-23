import { Box, For, HStack, Separator, Splitter, Stack, Text } from "@chakra-ui/react";
import ToggleExpandButton from "./ToggleExpandButton";
import { ResponsiveSidebarLayoutReducerStore } from "@/context/layout/responsive-sidebar-layout";
import SearchBar from "./SearchBar";
import TabButton, { ChatSidebarTabButtonProps } from "./tab/TabButton";
import { LuMessageCircleMore, LuNotebookText, LuPanelLeftClose, LuUserRound } from "react-icons/lu";
import { ChatSidebarTree } from "./tree/Tree";
import ChatSidebarTreeContainer from "./tree/TreeContainer";
import { ChatSidebarTab, ChatSidebarTabLinkMap } from "@/constants/enum/chat-sidebar-tab";

const tabs: ChatSidebarTabButtonProps[] = [
  {
    icon: <LuMessageCircleMore />,
    text: "Chat",
    link: ChatSidebarTabLinkMap(ChatSidebarTab.conversation),
  },
  {
    icon: <LuNotebookText />,
    text: "Project",
    link: ChatSidebarTabLinkMap(ChatSidebarTab.project),
  },
]

export default function ChatSidebar() {
  const { useSelector, useDispatch } = ResponsiveSidebarLayoutReducerStore;
  const dispatch = useDispatch();
  const isExpanded = useSelector(s => s.isExpanded);

  return (
    <Box bgColor={"bg.panel"} flexGrow={1} h="full" w="full" overflowY={"auto"} p={2}>
      <Stack alignItems={isExpanded ? "flex-start" : "center"} h={"full"}>
        <HStack w="full" justifyContent={isExpanded ? "flex-end" : "center"}>
          <ToggleExpandButton />
        </HStack>
        <Separator w="full" />
        <SearchBar />
        <Splitter.Root
          panels={[{ id: "mp" }, { id: "cb" }]}
          defaultSize={[35, 75]}
          orientation="vertical"
          borderWidth="1px"
          minH="60"
          flexGrow={1}
        // onResize={(details) => details.}
        >
          <Splitter.Panel id="mp">
            <For
              each={tabs}
            >
              {(item, index) => (
                <TabButton key={index} {...item} />
              )}
            </For>
          </Splitter.Panel>
          <Splitter.ResizeTrigger id="mp:cb" />
          <Splitter.Panel id="cb">
            <ChatSidebarTreeContainer>
              <ChatSidebarTree />
            </ChatSidebarTreeContainer>
          </Splitter.Panel>
        </Splitter.Root>
        <HStack></HStack>
      </Stack>
    </Box>
  )
}
