"use client"

import Sidebar from "@/components/chat/layout/sidebar/Sidebar";
import ResponsiveSidebarLayout from "@/components/layout/ResponsiveSidebarLayout";
import { ChatSidebarProvider } from "@/context/chat/chat-sidebar";
import { ResponsiveSidebarLayoutProvider } from "@/context/layout/responsive-sidebar-layout";
import { Flex, useBreakpointValue } from "@chakra-ui/react";

export default function ChatLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const isDesktop = useBreakpointValue({ base: false, lg: true });

  return (
    <ResponsiveSidebarLayoutProvider initialState={{ isDesktop }}>
      <ChatSidebarProvider>
        <Flex h="100vh" w="100vw">
          <ResponsiveSidebarLayout sidebar={<Sidebar />} content={children} isDesktop={isDesktop} flexGrow={1} />
        </Flex>
      </ChatSidebarProvider>
    </ResponsiveSidebarLayoutProvider>
  );
}
