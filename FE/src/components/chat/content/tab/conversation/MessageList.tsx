"use client"

import { ChatMessage } from "@/types/chat/ChatMessage";
import { ScrollArea } from "@chakra-ui/react";
import { useVirtualizer } from "@tanstack/react-virtual";
import { useEffect, useRef, useState } from "react";

async function getRandomTestMessages(numberOfMessages: number) {
  const apiResponse = await fetch("https://lorem-api.com/api/lorem?paragraphs=" + numberOfMessages);
  const paragraphs = await apiResponse.text().then(r => r.split("\n"))

  const ids = Array.from(Array(numberOfMessages), () => Math.round(Math.random()));
  const now = new Date();
  const result: ChatMessage[] = paragraphs.map((para, index) => ({
    id: index.toString(),
    conversationId: "1",
    senderId: ids[index].toString(),
    content: para,
    metaData: "",
    createdAt: now,
    updatedAt: now,
  }))
  return result;
}

export default function ConversationTabContentMessageList() {
  const [messages, setMessages] = useState<ChatMessage[]>([])

  const parentRef = useRef<HTMLDivElement | null>(null)

  const [enabled, setEnabled] = useState(true)

  const count = messages.length
  const virtualizer = useVirtualizer({
    count,
    getScrollElement: () => parentRef.current,
    estimateSize: () => 45,
    enabled,
    overscan: 5,
  })

  const items = virtualizer.getVirtualItems()

  useEffect(() => {
    const wrap = async () => {
      const m = await getRandomTestMessages(100)
      setMessages(m);
      // virtualizer.scrollToIndex(count - 1, { align: "end" })
    }
    wrap()
  }, [])

  return (
    <ScrollArea.Root minH={0} maxH={"full"} display={"flex"} flexDirection={"column-reverse"}>
      <ScrollArea.Viewport ref={parentRef}
        h={virtualizer.getTotalSize()}
        w={"full"}
      >
        <ScrollArea.Content
          position={"relative"}
          w={"full"}
        >
          <div
            style={{
              position: 'absolute',
              top: 0,
              left: 0,
              width: '100%',
              transform: `translateY(${items[0]?.start ?? 0}px)`,
            }}
          >
            {items.map((virtualRow) => (
              <div
                key={virtualRow.key}
                data-index={virtualRow.index}
                ref={virtualizer.measureElement}
                className={
                  virtualRow.index % 2 ? 'ListItemOdd' : 'ListItemEven'
                }
              >
                <div style={{ padding: '10px 0' }}>
                  <div>Row {virtualRow.index}</div>
                  <div>{virtualizer.getTotalSize()}</div>
                  <div>{items[0]?.start ?? 0}</div>
                  <div>{JSON.stringify(messages[virtualRow.index])}</div>
                </div>
              </div>
            ))}
          </div>
        </ScrollArea.Content>
      </ScrollArea.Viewport>
      <ScrollArea.Scrollbar>
        <ScrollArea.Thumb style={{ transform: "translate3d(0px, 1px, 0px)" }} />
      </ScrollArea.Scrollbar>
      <ScrollArea.Corner />
    </ScrollArea.Root>
  );
}
