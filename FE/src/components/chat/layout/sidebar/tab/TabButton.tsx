import { ChatSidebarTab } from "@/constants/enum/chat-sidebar-tab";
import { ChatSidebarReducerStore } from "@/context/chat/chat-sidebar";
import { ResponsiveSidebarLayoutReducerStore } from "@/context/layout/responsive-sidebar-layout";
import { HStack, IconButton } from "@chakra-ui/react"
import { useMemo } from "react";
import { IconType } from "react-icons";

export type ChatSidebarTabButtonProps = {
  icon: IconType
  text: string
  onClick?: () => void
  tab?: ChatSidebarTab
}

export default function ChatSidebarTabButton(props: ChatSidebarTabButtonProps) {
  const { icon, text, onClick, tab } = props;

  const { useSelector: useLayoutSelector, useDispatch: useLayoutDispatch } = ResponsiveSidebarLayoutReducerStore;
  const layoutDispatch = useLayoutDispatch();
  const isExpanded = useLayoutSelector(s => s.isExpanded);

  const { useSelector: useSidebarSelector, useDispatch: useSidebarDispatch } = ChatSidebarReducerStore;
  const sidebarDispatch = useSidebarDispatch();
  const currentTab = useSidebarSelector(s => s.currentTab);

  const isActive = useMemo(() => {
    if (tab && tab == currentTab) {
      return true
    }
    return false
  }, [tab, currentTab])

  return (
    <IconButton
      onClick={() => {
        if (onClick) onClick()
        if (tab) sidebarDispatch({ type: "SET_CURRENT_TAB", payload: tab })
      }}
      variant={isActive ? "solid" : "outline"}
      aria-label={text}
      w="full"
    >
      <HStack justifyContent={"start"} w="full" px={2}>
        {icon({})}{isExpanded && text}
      </HStack>
    </IconButton>
  )
}
