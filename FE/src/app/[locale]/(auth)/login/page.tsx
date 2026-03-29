import LoginBox from "@/components/auth/LoginBox";
import { ColorModeButton } from "@/components/ui/color-mode";
import { Center, HStack, Stack } from "@chakra-ui/react";

export default function Page() {
  return (
    <Stack w="100vw" h="100vh">
      <HStack justifyContent={"flex-end"}>
        <ColorModeButton />
      </HStack>
      <Center flexGrow={1}>
        <LoginBox />
      </Center>
    </Stack>
  );
}
