"use client"

import Sidebar from "@/components/chat/layout/sidebar/Sidebar";
import { ChatSidebarTreeNode } from "@/components/chat/layout/sidebar/tree/Tree";
import ResponsiveSidebarLayout from "@/components/layout/ResponsiveSidebarLayout";
import { ChatSidebarProvider, ChatSidebarReducerStore } from "@/context/chat/chat-sidebar";
import { ResponsiveSidebarLayoutProvider } from "@/context/layout/responsive-sidebar-layout";
import { createTreeCollection, Flex, TreeCollection, useBreakpointValue } from "@chakra-ui/react";
import { useEffect } from "react";

const EXAMPLE_TREE_COLLECTION: TreeCollection<ChatSidebarTreeNode> = createTreeCollection<ChatSidebarTreeNode>({
  nodeToValue: (node) => node.id,
  nodeToString: (node) => node.name,
  rootNode: {
    id: "ROOT",
    name: "",
    children: [{
      id: "COL1",
      name: "Col 1",
      href: "conversation/col1",
    }]
  }
})

export default function ConversationTabLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const { useSelector: useSidebarSelector, useDispatch: useSidebarDispatch } = ChatSidebarReducerStore;
  const sidebarDispatch = useSidebarDispatch();

  useEffect(() => {
    sidebarDispatch({ type: "SET_TREE_COLLECTION_QUERY", payload: EXAMPLE_TREE_COLLECTION })
  }, [sidebarDispatch])

  return (
    <>{children}</>
  );
}
