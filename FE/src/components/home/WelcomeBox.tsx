"use client"
import { Box, Text, Link as ChakraLink, Stack, SkeletonText } from "@chakra-ui/react";
import AuthenticatedContent from "../layout/AuthenticatedContent";
import { UserState } from "@/types/UserState";
import { useAppSelector } from "@/service/hooks";
import Link from "next/link";
import { AnyAccessPrivileges } from "@/types/AccessPrivileges";
import check from "check-types";
import { useHealthCheckQuery } from "@/service/api/testApiSlice";
import DataLoading from "../common/DataLoading";
import DataFetchError from "../common/DataFetchError";
import { tryGetApiErrorMsg } from "@/utils/tryGetApiErrorMsg";
import { FormattedText } from "@/lib/intl/FormattedText";

const adminPrivs: AnyAccessPrivileges[] = ["system-admin", "system-read", "system-readWrite"]

export default function WelcomeBox() {
  const userState: UserState | null = useAppSelector((state) =>
    state.auth.userState
  );

  const isAdmin = check.array.of.string(userState?.accessPrivileges) ? adminPrivs.find(rp => check.contains(userState.accessPrivileges, rp)) : false

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
    <AuthenticatedContent
      unauthenticatedContent={
        <Box>
          <Text>You are not logged in</Text>
          <Stack mt={5}>
            <Link href={"/login"}>
              <ChakraLink colorPalette={"cyan"} as="p">
                login
              </ChakraLink>
            </Link>
          </Stack>

          <Box>
            <Text>{healthCheckQuery?.data}</Text>
          </Box>
        </Box>
      }
    >
      <Box>
        <FormattedText id={"welcomeMessage"} values={{ name: userState?.name }} placeholder={<SkeletonText noOfLines={1} />} />
        {isAdmin && <Text colorPalette={"red"} color={"colorPalette.info"}>You have admin privileges</Text>}
        <Stack mt={5}>
          <Link href={"/dashboard"}>
            <ChakraLink colorPalette={"cyan"} as="p">
              Go to dashboard
            </ChakraLink>
          </Link>
          {isAdmin && <Link href={"/admin-dashboard"}>
            <ChakraLink colorPalette={"cyan"} as="p">
              Go to admin dashboard
            </ChakraLink>
          </Link>}
        </Stack>
      </Box>
    </AuthenticatedContent>
  );
}
