"use client";

import { ChakraProvider, defaultSystem } from "@chakra-ui/react";
import { ColorModeProvider, type ColorModeProviderProps } from "./color-mode";
import { useEffect, useState } from "react";
import store from "@/service/store";
import { hydrate as hydrateUserState, setAccessToken, setCredentials, setIsRefreshingToken } from "@/service/features/authSlice";
import { Provider as ReduxStoreProvider } from "react-redux";
import { useTokenRefreshQuery } from "@/service/api/authApiSlice";
import { IntlProvider, IntlProviderProps, LocaleKey, LocaleMessages } from "@/lib/intl/intl";
import { importMessages } from "@/lib/intl/importMessages";
import { WebSocketClientProvider } from "@/lib/websocket/client";
import { tryGetApiErrorCode } from "@/utils/tryGetApiErrorCode";
import { toaster } from "./toaster";
import { useFormatMessage } from "@/lib/intl/useFormatMessage";

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
  const t = useFormatMessage();

  if (!isFetchingToken) {
    if (!fetchError) {
      const res = getRefreshTokenQuery?.data;
      store.dispatch(setCredentials(res?.userState ?? null));
      store.dispatch(setAccessToken(res?.accessToken ?? null));
    }
    else {
      const error = tryGetApiErrorCode(fetchError);

      toaster.create({
        // TODO: Change to auth state result failed
        title: t("auth.login.result.failed"),
        description: t(`errors.auth.state.${error}` as unknown as LocaleKey,
          {},
          // TODO: Change to auth state default error
          t("auth.login.result.failed.defaultError")),
        type: "error",
      });

      // TODO: Handle extra actions, such as redirect on ERR_AUTHENTICATED_ONLY error
      store.dispatch(setCredentials(null));
      store.dispatch(setAccessToken(null));
    }
  }

  useEffect(() => {
    store.dispatch(setIsRefreshingToken(isFetchingToken));
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
              <WebSocketClientProvider>
                {props.children}
              </WebSocketClientProvider>
            </ColorModeProvider>
          </ChakraProvider>
        </ReduxHydrator>
      </ReduxStoreProvider>
    </IntlProvider>
  );
}
