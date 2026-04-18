import Test from "@/components/dev/Test";
import { Stack, Text } from "@chakra-ui/react";

export default async function Page({
  params,
}: Readonly<{
  params: Promise<{ id: string }>;
}>) {
  const { id } = await params;

  return (
    <Stack w="100vw" h="100vh">
      <Text>Room ID: {id}</Text>
      <Test roomId={id} />
    </Stack>
  );
}

