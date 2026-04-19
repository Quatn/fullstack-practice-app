"use client"

import Sidebar from "@/components/chat/layout/sidebar/Sidebar";
import ResponsiveSidebarLayout from "@/components/layout/ResponsiveSidebarLayout";
import { ResponsiveSidebarLayoutProvider } from "@/context/layout/responsive-sidebar-layout";
import { useBreakpointValue } from "@chakra-ui/react";

export default function ChatLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const isDesktop = useBreakpointValue({ base: false, lg: true });

  return (
    <ResponsiveSidebarLayoutProvider initialState={{ isDesktop }}>
      <ResponsiveSidebarLayout sidebar={<Sidebar />} content={children} isDesktop={isDesktop} />
    </ResponsiveSidebarLayoutProvider>
  );
}
