"use client"

import { ChatSidebarReducerStore } from "@/context/chat/chat-sidebar";
import { useEffect } from "react";

export default function ProjectTabLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const { useSelector: useSidebarSelector, useDispatch: useSidebarDispatch } = ChatSidebarReducerStore;
  const sidebarDispatch = useSidebarDispatch();

  useEffect(() => {
    sidebarDispatch({ type: "SET_TREE_COLLECTION_QUERY", payload: null })
  }, [sidebarDispatch])

  return (
    <>{children}</>
  );
}
