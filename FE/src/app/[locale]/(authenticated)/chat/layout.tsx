"use client"

import SideBar from "@/components/chat/layout/sidebar/SideBar";
import { Box, Button, useBreakpointValue } from "@chakra-ui/react";
import { useMemo, useState } from "react";

export default function ChatLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const isDesktop = useBreakpointValue({ base: false, lg: true });
  const [isDirty, setIsDirty] = useState(false);
  const [isManuallyExpanded, setIsManuallyExpanded] = useState(true);
  const isExpanded = useMemo(() => {
    if (isDirty) {
      return isManuallyExpanded;
    }
    return !!isDesktop;
  }, [isDesktop, isDirty, isManuallyExpanded]);

  return (
    <Box position="relative">
      {/* Sidebar */}
      <Box
        position={isDesktop ? "relative" : "fixed"}
        left={0}
        w={isExpanded ? "240px" : "80px"}
        transition="all 0.2s"
      >
        <Button onClick={() => {
          setIsManuallyExpanded(!isManuallyExpanded)
          setIsDirty(true)
        }}>Extent</Button>
        <SideBar />
      </Box>

      {/* Content */}
      <Box
        ml={
          isDesktop
            ? (isExpanded ? "240px" : "80px")
            : "80px"
        }
        transition="margin 0.2s"
      >
        {children}
      </Box>
    </Box>
  );
}
