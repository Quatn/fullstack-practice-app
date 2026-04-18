'use client'

import { useHealthCheckQuery } from "@/service/api/testApiSlice";
import DataLoading from "../common/DataLoading";
import DataFetchError from "../common/DataFetchError";
import { tryGetApiErrorMsg } from "@/utils/tryGetApiErrorMsg";
import { Box, Button, Text } from "@chakra-ui/react";
import { useWebSocketClient } from "@/lib/websocket/client";
import { useEffect } from "react";
import { toaster } from "../ui/toaster";
import { config } from "@/config/config";

export default function Test({ roomId }: { roomId: string }) {
  const {
    data: healthCheckQuery,
    error: fetchError,
    isFetching: isFetchingList,
  } = useHealthCheckQuery();

  const socket = useWebSocketClient()

  useEffect(() => {
    const subscription = socket.watch(config.WS_BROKER_ENDPOINT + `/greetings/room.${roomId}`).subscribe((message) => {
      toaster.info({
        title: "Message From Server",
        description: message.body
      })
    });

    return () => {
      console.log("Cleaning up subscription...");
      subscription.unsubscribe();
    };
  }, [socket, roomId]);

  if (isFetchingList) {
    return <DataLoading />
  }

  if (fetchError) {
    return <DataFetchError h={"full"} flexGrow={1} errorText={tryGetApiErrorMsg(fetchError)} />;
  }

  const handleTestSocket = () => {
    socket.publish({
      destination: config.WS_APPLICATION_DESTINATION_PREFIX + `/hello/${roomId}`,
      body: JSON.stringify({ name: "World" })
    });
  }

  return (
    <Box>
      <Text>{JSON.stringify(healthCheckQuery)}</Text>
      <Button onClick={handleTestSocket}>Test</Button>
    </Box>
  );
}
