"use client"

import { ResponsiveSidebarLayoutReducerStore } from "@/context/layout/responsive-sidebar-layout";
import { Box, Button } from "@chakra-ui/react";
import check from "check-types";
import { useEffect } from "react";

export default function ResponsiveSidebarLayout({
  sidebar,
  content,
  isDesktop,
}: Readonly<{
  sidebar: React.ReactNode;
  content: React.ReactNode;
  isDesktop?: boolean;
}>) {
  const { useSelector, useDispatch } = ResponsiveSidebarLayoutReducerStore;
  const dispatch = useDispatch();
  const isExpanded = useSelector((s) => s.isExpanded);

  useEffect(() => {
    if (!check.undefined(isDesktop)) {
      dispatch({ type: "SET_IS_DESKTOP", payload: isDesktop })
    }
  }, [dispatch, isDesktop])

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
          dispatch({ type: "TOGGLE_EXPAND" })
        }}>Extent</Button>
        {sidebar}
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
        {content}
      </Box>
    </Box>
  );
}
