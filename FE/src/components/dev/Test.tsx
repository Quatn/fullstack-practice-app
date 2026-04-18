'use client'

import { useHealthCheckQuery } from "@/service/api/testApiSlice";
import DataLoading from "../common/DataLoading";
import DataFetchError from "../common/DataFetchError";
import { tryGetApiErrorMsg } from "@/utils/tryGetApiErrorMsg";
import { Box, Button, Text } from "@chakra-ui/react";
import { useWebSocketClient } from "@/lib/websocket/client";

export default function Test() {
  const {
    data: healthCheckQuery,
    error: fetchError,
    isFetching: isFetchingList,
  } = useHealthCheckQuery();

  const socket = useWebSocketClient()

  if (isFetchingList) {
    return <DataLoading />
  }

  if (fetchError) {
    return <DataFetchError h={"full"} flexGrow={1} errorText={tryGetApiErrorMsg(fetchError)} />;
  }

  const handleTestSocket = () => {
    console.log(socket.connected())
  }

  return (
    <Box>
      <Text>{JSON.stringify(healthCheckQuery)}</Text>
      <Button onClick={handleTestSocket}>Test</Button>
    </Box>
  );
}
