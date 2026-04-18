"use client"

import { config } from "@/config/config";
import { RxStomp } from "@stomp/rx-stomp";
import { createContext, useContext } from "react";
import SockJS from "sockjs-client";

export const SOCKET_URL = process.env.NEXT_PUBLIC_API_URL + config.WS_SOCKET_URL_ENDPOINT;

export type WebSocketClientProviderProviderProps = {
  children: React.ReactNode,
}

const Context = createContext<RxStomp | null>(null);

export const WebSocketClientProvider: React.FC<WebSocketClientProviderProviderProps> = props => {
  const rxStomp = new RxStomp();
  rxStomp.configure({
    webSocketFactory: () => new SockJS(SOCKET_URL),
    heartbeatIncoming: config.WS_HEART_BEAT_IN_COMING,
    heartbeatOutgoing: config.WS_HEART_BEAT_OUT_GOING,
    reconnectDelay: config.WS_RECONNECT_DELAY,
  });
  rxStomp.activate();

  return (
    <Context value={rxStomp}>{props.children}</Context>
  )
}

export function useWebSocketClient() {
  const client = useContext(Context);
  if (!client) throw new Error("useWebSocketClient must be used inside WebSocketClientProvider");
  return client;
}
