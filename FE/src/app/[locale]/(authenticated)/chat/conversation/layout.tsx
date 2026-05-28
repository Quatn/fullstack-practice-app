"use client"

import ConversationManagementDialog from "@/components/chat/content/dialog/conversation-management/Dialog";
import Sidebar from "@/components/chat/layout/sidebar/Sidebar";
import { ChatSidebarTreeNode } from "@/components/chat/layout/sidebar/tree/Tree";
import ResponsiveSidebarLayout from "@/components/layout/ResponsiveSidebarLayout";
import { ChatSidebarProvider, ChatSidebarReducerStore } from "@/context/chat/chat-sidebar";
import { ChatConversationManagementDialogProvider, ChatConversationManagementDialogReducerStore } from "@/context/chat/dialog/conversation-management-dialog";
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

type ConversationTabLayoutProps = {
  children: React.ReactNode;
}

export function ConversationTabLayout({
  children,
}: Readonly<ConversationTabLayoutProps>) {
  const { useSelector: useSidebarSelector, useDispatch: useSidebarDispatch } = ChatSidebarReducerStore;
  const sidebarDispatch = useSidebarDispatch();

  const { useSelector: useDialogSelector, useDispatch: useDialogDispatch } = ChatConversationManagementDialogReducerStore;
  const dialogDispatch = useDialogDispatch();

  useEffect(() => {
    sidebarDispatch({ type: "SET_TREE_COLLECTION_QUERY", payload: EXAMPLE_TREE_COLLECTION })
    sidebarDispatch({
      type: "SET_PRIMARY_ACTION_BUTTON_PROPS",
      payload: {
        label: "",
        onClick: () => {
          dialogDispatch({ type: "SET_IS_OPEN", payload: true })
        }
      }
    })
  }, [sidebarDispatch, dialogDispatch])

  return (
    <>{children}</>
  );
}

export default function Layout(props: Readonly<ConversationTabLayoutProps>) {
  return (
    <ChatConversationManagementDialogProvider>
      <ConversationTabLayout {...props} />
      <ConversationManagementDialog />
    </ChatConversationManagementDialogProvider>

  );
}

