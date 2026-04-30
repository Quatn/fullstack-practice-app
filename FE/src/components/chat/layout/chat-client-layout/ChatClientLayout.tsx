"use client"

import { Box, Splitter, Stack } from "@chakra-ui/react";
import { useState } from "react";

export type ChatClientLayoutProps = {
  messagePanel: React.ReactNode,
  messageComposeBar: React.ReactNode,
}

export default function ChatClientLayout(props: ChatClientLayoutProps) {
  const { messagePanel, messageComposeBar } = props;

  const [sizes, setSizes] = useState([50, 50])

  return (
    <Stack flexGrow={1}>
      <Box flexGrow={1}>
        {messagePanel}
      </Box>
      <Box>
        {messageComposeBar}
      </Box>
    </Stack>
  );
}
