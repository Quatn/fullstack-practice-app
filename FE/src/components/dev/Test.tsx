'use client'

import { useHealthCheckQuery } from "@/service/api/testApiSlice";
import DataLoading from "../common/DataLoading";
import DataFetchError from "../common/DataFetchError";
import { tryGetApiErrorMsg } from "@/utils/tryGetApiErrorMsg";
import { Box, Button, Text } from "@chakra-ui/react";

export default function Test() {
  const {
    data: healthCheckQuery,
    error: fetchError,
    isFetching: isFetchingList,
  } = useHealthCheckQuery();

  if (isFetchingList) {
    return <DataLoading />
  }

  if (fetchError) {
    return <DataFetchError h={"full"} flexGrow={1} errorText={tryGetApiErrorMsg(fetchError)} />;
  }

  return (
    <Box>
      <Text>{JSON.stringify(healthCheckQuery)}</Text>
    </Box>
  );
}
