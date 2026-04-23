"use client"

import { Splitter } from "@chakra-ui/react";
import { useState } from "react";

export type ChatClientLayoutProps = {
  messagePanel: React.ReactNode,
  messageComposeBar: React.ReactNode,
}

export default function ChatClientLayout(props: ChatClientLayoutProps) {
  const { messagePanel, messageComposeBar } = props;

  const [sizes, setSizes] = useState([50, 50])

  return (
    <Splitter.Root
      panels={[{ id: "mp" }, { id: "cb", collapsible: true, collapsedSize: 1 }]}
      orientation="vertical"
      borderWidth="1px"
      minH="60"
      // onResize={(details) => details.}
    >
      <Splitter.Panel id="mp">
        {messagePanel}
      </Splitter.Panel>
      <Splitter.ResizeTrigger id="mp:cb" />
      <Splitter.Panel id="cb" height={"60px"}>
        {messageComposeBar}
      </Splitter.Panel>
    </Splitter.Root>
  );
}
