import { Box, HStack, Stack, Text } from "@chakra-ui/react";
import ToggleExpandButton from "./ToggleExpandButton";

export default function Sidebar() {
  return (
    <Box bgColor={"gray.100"} flexGrow={1} h="full" w="full" overflowY={"auto"}>
      <Stack>
        <HStack justifyContent={"flex-end"}>
          <ToggleExpandButton />
        </HStack>
        <Text>SideBar</Text>
      </Stack>
    </Box>
  )
}
