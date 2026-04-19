import { Flex, Text } from "@chakra-ui/react";

export default function Home() {
  return (
    <Flex direction={"column"} grow={1} bgColor={"green.100"} h="full" w="full">
      <Text>Content</Text>
    </Flex>
  );
}
