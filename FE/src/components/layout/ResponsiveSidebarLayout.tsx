"use client"

import { ResponsiveSidebarLayoutReducerStore } from "@/context/layout/responsive-sidebar-layout";
import { Box, HStack, StackProps } from "@chakra-ui/react";
import check from "check-types";
import { useEffect } from "react";

export type ResponsiveSidebarLayoutProps = Omit<StackProps, "children" | "content"> & {
  sidebar: React.ReactNode;
  content: React.ReactNode;
  isDesktop?: boolean;
  sidebarRetractedWidth?: number | string;
  sidebarExpandedWidth?: number | string;
}

export default function ResponsiveSidebarLayout(props: Readonly<ResponsiveSidebarLayoutProps>) {
  const { sidebar, content, isDesktop, sidebarRetractedWidth, sidebarExpandedWidth, ...restOfProps } = props;
  const retractedWidth = sidebarRetractedWidth ?? "4rem"
  const expandedWidth = sidebarExpandedWidth ?? "18rem"
  const { useSelector, useDispatch } = ResponsiveSidebarLayoutReducerStore;
  const dispatch = useDispatch();
  const isExpanded = useSelector((s) => s.isExpanded);

  useEffect(() => {
    if (!check.undefined(isDesktop)) {
      dispatch({ type: "SET_IS_DESKTOP", payload: isDesktop })
    }
  }, [dispatch, isDesktop])

  return (
    <HStack
      position="relative"
      w="full"
      h="full"
      alignItems={"start"}
      gapX={0}
      {...restOfProps}
    >
      {/* Sidebar */}
      <Box
        position={isDesktop ? "relative" : "fixed"}
        h="full"
        left={0}
        w={isExpanded ? expandedWidth : retractedWidth}
        transition="all 0.2s"
      >
        {sidebar}
      </Box>

      {/* Content */}
      <Box
        h="full"
        ml={isDesktop ? 0 : retractedWidth}
        transition="margin 0.2s"
        flexGrow={1}
      >
        {content}
      </Box>
    </HStack>
  );
}
