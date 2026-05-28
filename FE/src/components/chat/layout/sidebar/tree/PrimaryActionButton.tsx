"use client";

import { ChatSidebarReducerStore } from "@/context/chat/chat-sidebar";
import { Box, Button, useTreeViewContext } from "@chakra-ui/react";
import { useMemo } from "react";

export default function ChatSidebarTreePrimaryActionButton() {
  const { useSelector } = ChatSidebarReducerStore;
  const primaryActionButtonProps = useSelector(s => s.primaryActionButtonProps);

  return (
    <Box>
      {primaryActionButtonProps && <Button
        aria-label={"primaryActionButton"}
        size={"2xs"}
        colorPalette={"white"}
        onClick={primaryActionButtonProps.onClick}
        {...primaryActionButtonProps.style}
      >
        Đóng
      </Button>}
    </Box>
  );
}
