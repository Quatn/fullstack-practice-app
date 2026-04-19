"use client"

import Sidebar from "@/components/chat/layout/sidebar/Sidebar";
import ResponsiveSidebarLayout from "@/components/layout/ResponsiveSidebarLayout";
import { ChatLayoutProvider } from "@/context/chat/chat-layout-context";
import { useBreakpointValue } from "@chakra-ui/react";

export default function ChatLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const isDesktop = useBreakpointValue({ base: false, lg: true });

  return (
    <ChatLayoutProvider>
      <ResponsiveSidebarLayout sidebar={<Sidebar />} content={children} isDesktop={isDesktop} />
    </ChatLayoutProvider>
  );
}
