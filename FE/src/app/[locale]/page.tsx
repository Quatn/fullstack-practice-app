import WelcomeBox from "@/components/home/WelcomeBox";
import { FormattedText } from "@/lib/intl/FormattedText";
import { Box, Flex, SkeletonText } from "@chakra-ui/react";

export default function Home() {
  return (
    <Flex minH={"100vh"} direction={"column"} grow={1}>
      {/*
      <Header />
      */}
      <main style={{ flexGrow: 1 }}>
        <Box m={5} p={2} rounded={"sm"}>
          <FormattedText id={"app.title"} placeholder={<SkeletonText />} fontWeight={"semibold"} colorPalette={"black"} fontSize={"2rem"} />
          <WelcomeBox />
        </Box>
      </main>
      <footer></footer>
    </Flex>
  );
}
