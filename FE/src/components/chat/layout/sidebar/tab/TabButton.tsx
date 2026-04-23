'use client'

import { ChatSidebarTab } from "@/constants/enum/chat-sidebar-tab";
import { ChatSidebarReducerStore } from "@/context/chat/chat-sidebar";
import { ResponsiveSidebarLayoutReducerStore } from "@/context/layout/responsive-sidebar-layout";
import { urlDirMatch } from "@/utils/urlDirMatch";
import { ButtonProps, HStack, IconButton } from "@chakra-ui/react"
import { usePathname, useRouter } from "next/navigation";
import { ReactNode, useMemo } from "react";
import { IconType } from "react-icons";

const LINK_PREFIX = "/chat/";
const MAX_DIR_MATCH_DEPTH = 2;

export type ChatSidebarTabButtonProps = ButtonProps & {
  icon: ReactNode
  text: string
  link?: string
}

export default function ChatSidebarTabButton(props: ChatSidebarTabButtonProps) {
  const router = useRouter()

  const { icon, text, onClick, link } = props;

  const pathname = usePathname()

  const { useSelector: useLayoutSelector, useDispatch: useLayoutDispatch } = ResponsiveSidebarLayoutReducerStore;
  const layoutDispatch = useLayoutDispatch();
  const isExpanded = useLayoutSelector(s => s.isExpanded);

  const { useSelector: useSidebarSelector, useDispatch: useSidebarDispatch } = ChatSidebarReducerStore;
  const sidebarDispatch = useSidebarDispatch();

  const isActive = useMemo(() => {
    if (link && urlDirMatch(pathname, link, MAX_DIR_MATCH_DEPTH)) {
      return true
    }
    return false
  }, [link, pathname])

  return (
    <IconButton
      onClick={(v) => {
        if (onClick) onClick(v);
        if (link) router.push(LINK_PREFIX + link);
      }}
      variant={isActive ? "solid" : "outline"}
      aria-label={text}
      w="full"
    >
      <HStack justifyContent={"start"} w="full" px={2}>
        {icon}{isExpanded && text}
      </HStack>
    </IconButton>
  )
}
