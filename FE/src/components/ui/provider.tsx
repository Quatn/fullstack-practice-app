"use client";

import { ChakraProvider, defaultSystem } from "@chakra-ui/react";
import { ColorModeProvider, type ColorModeProviderProps } from "./color-mode";
import { useEffect, useState } from "react";
import store from "@/service/store";
import { hydrate as hydrateUserState, setAccessToken, setCredentials, setRefreshingToken } from "@/service/features/authSlice";
import { Provider as ReduxStoreProvider } from "react-redux";
import { useTokenRefreshQuery } from "@/service/api/authApiSlice";
import { importMessages, IntlProvider, IntlProviderProps, LocaleMessages } from "@/lib/intl/intl";

export type ProviderProps = {
  intlProviderProps: Omit<IntlProviderProps, 'messages'>,
  colorModeProviderProps?: ColorModeProviderProps,
  children: React.ReactNode,
}

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

export function Provider(props: ProviderProps) {
  const [messages, setMessages] = useState<LocaleMessages | Record<string, string>>({})

  useEffect(() => {
    importMessages(props.intlProviderProps.locale).then((res) => {
      if (res != null) {
        setMessages(res)
      }
    })
  }, [props.intlProviderProps.locale])

  return (
    <IntlProvider {...props.intlProviderProps} messages={messages}>
      <ReduxStoreProvider store={store}>
        <ReduxHydrator>
          <ChakraProvider value={defaultSystem}>
            <ColorModeProvider {...props.colorModeProviderProps}>
              {props.children}
            </ColorModeProvider>
          </ChakraProvider>
        </ReduxHydrator>
      </ReduxStoreProvider>
    </IntlProvider>
  );
}
