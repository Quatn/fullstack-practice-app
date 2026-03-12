"use client";

import { ChakraProvider, defaultSystem } from "@chakra-ui/react";
import { ColorModeProvider, type ColorModeProviderProps } from "./color-mode";
import { useEffect } from "react";
import store from "@/service/store";
import { hydrate as hydrateUserState, setAccessToken, setCredentials, setRefreshingToken } from "@/service/features/authSlice";
import { Provider as ReduxStoreProvider } from "react-redux";
import { useTokenRefreshQuery } from "@/service/api/authApiSlice";

function ReduxHydrator({ children }: { children: React.ReactNode }) {
  const {
    data: getRefreshTokenQuery,
    error: fetchError,
    isFetching: isFetchingToken,
  } = useTokenRefreshQuery({});

  if (!isFetchingToken && !fetchError) {
    const res = getRefreshTokenQuery?.data;
    store.dispatch(setCredentials(res?.userState));
    store.dispatch(setAccessToken(res?.accessToken));
  }

  useEffect(() => {
    store.dispatch(setRefreshingToken(isFetchingToken));
  }, [isFetchingToken]);

  useEffect(() => {
    store.dispatch(hydrateUserState());
  }, []);

  return <>{children}</>;
}

export function Provider(props: ColorModeProviderProps) {
  return (
    <ReduxStoreProvider store={store}>
      <ReduxHydrator>
        <ChakraProvider value={defaultSystem}>
          <ColorModeProvider {...props} />
        </ChakraProvider>
      </ReduxHydrator>
    </ReduxStoreProvider>
  );
}
