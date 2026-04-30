"use client"

import { Store, useStore } from "@tanstack/react-store";
import React, { createContext, useContext } from "react";

interface StoreState {
  isMessageComposeBarExpanded: boolean;
}

export type ChatClientLayoutStoreState = StoreState

type StoreAction =
  | { type: "SET_EXPAND_MESSAGE_COMPOSE_BAR"; payload: boolean }
  | { type: "TOGGLE_EXPAND_MESSAGE_COMPOSE_BAR" }
  | { type: "RESET" };

const initialState: StoreState = {
  isMessageComposeBarExpanded: false,
};

function reducer(state: StoreState, action: StoreAction): StoreState {
  switch (action.type) {
    case "SET_EXPAND_MESSAGE_COMPOSE_BAR":
      return { ...state, isMessageComposeBarExpanded: action.payload };
    case "TOGGLE_EXPAND_MESSAGE_COMPOSE_BAR":
      return { ...state, isMessageComposeBarExpanded: !state.isMessageComposeBarExpanded };
    case "RESET":
      return initialState;
    default:
      return state;
  }
}

const StoreContext = createContext<Store<StoreState> | null>(null);

export function ChatClientLayoutProvider(
  props: { children: React.ReactNode, initialState?: Partial<StoreState> },
) {
  const [store] = React.useState(
    () => {
      if (props.initialState) {
        return new Store<StoreState>({ ...props.initialState, ...initialState })
      }
      return new Store<StoreState>(initialState)
    }
  );

  return (
    <StoreContext.Provider value={store}>
      {props.children}
    </StoreContext.Provider>
  );
}

// Internal hook to get the store
function useStoreInstance() {
  const store = useContext(StoreContext);
  if (!store) throw new Error("ChatClientLayoutReducerStore functions must be used inside ChatClientLayoutProvider");
  return store;
}

// Select slices of state
function useSelector<T>(selector: (state: StoreState) => T): T {
  const store = useStoreInstance();
  return useStore(store, selector);
}

// Dispatch reducer actions
function useDispatch() {
  const store = useStoreInstance();
  return (action: StoreAction) => {
    store.setState((prev) => reducer(prev, action));
  };
}

export const ChatClientLayoutReducerStore = {
  context: StoreContext,
  useStoreInstance,
  useSelector: useSelector,
  useDispatch: useDispatch,
}
