"use client"

import ConversationManagementDialog from "@/components/chat/content/dialog/conversation-management/Dialog";
import Sidebar from "@/components/chat/layout/sidebar/Sidebar";
import { ChatSidebarTreeNode } from "@/components/chat/layout/sidebar/tree/Tree";
import ResponsiveSidebarLayout from "@/components/layout/ResponsiveSidebarLayout";
import { ChatSidebarProvider, ChatSidebarReducerStore } from "@/context/chat/chat-sidebar";
import { ChatConversationManagementDialogProvider, ChatConversationManagementDialogReducerStore } from "@/context/chat/dialog/conversation-management-dialog";
import { ResponsiveSidebarLayoutProvider } from "@/context/layout/responsive-sidebar-layout";
import { useListConversationQuery } from "@/service/api/conversationApiSlice";
import { CONVERSATION_URL } from "@/service/constants";
import { createTreeCollection, Flex, TreeCollection, useBreakpointValue } from "@chakra-ui/react";
import check from "check-types";
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

  const {
    data: listConversationPaginatedResponse,
    error: fetchError,
    isFetching: isFetchingList,
    refetch: refetchTable,
  } = useListConversationQuery({});

  useEffect(() => {
    const listConversationPaginatedList = listConversationPaginatedResponse?.data;
    if (check.undefined(listConversationPaginatedList) || !!fetchError) {
      sidebarDispatch({ type: "SET_TREE_COLLECTION_QUERY", payload: EXAMPLE_TREE_COLLECTION })
    }
    else {
      const col: TreeCollection<ChatSidebarTreeNode> = createTreeCollection<ChatSidebarTreeNode>({
        nodeToValue: (node) => node.id,
        nodeToString: (node) => node.name,
        rootNode: {
          id: "ROOT",
          name: "",
          children:
            listConversationPaginatedList.data.map(conv => ({
              id: conv.id,
              name: conv.name,
              href: `${CONVERSATION_URL}/${conv.id}`,
            }))
        }
      })

      sidebarDispatch({ type: "SET_TREE_COLLECTION_QUERY", payload: col })
    }
    sidebarDispatch({
      type: "SET_PRIMARY_ACTION_BUTTON_PROPS",
      payload: {
        label: "",
        onClick: () => {
          dialogDispatch({ type: "SET_IS_OPEN", payload: true })
        }
      }
    })
  }, [sidebarDispatch, dialogDispatch, listConversationPaginatedResponse])

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

