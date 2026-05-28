"use client"

import { Store, useStore } from "@tanstack/react-store";
import React, { createContext, useContext } from "react";

interface StoreState {
  isOpen: boolean;
}

export type ChatConversationManagementDialogStoreState = StoreState

type StoreAction =
  | { type: "SET_IS_OPEN"; payload: boolean }
  | { type: "TOGGLE_OPEN"; }
  | { type: "RESET" };

const initialState: StoreState = {
  isOpen: false,
};

function reducer(state: StoreState, action: StoreAction): StoreState {
  switch (action.type) {
    case "SET_IS_OPEN":
      return { ...state, isOpen: action.payload };
    case "TOGGLE_OPEN":
      return { ...state, isOpen: !state.isOpen };
    case "RESET":
      return initialState;
    default:
      return state;
  }
}

const StoreContext = createContext<Store<StoreState> | null>(null);

export function ChatConversationManagementDialogProvider(
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
  if (!store) throw new Error("ChatConversationManagementDialogReducerStore functions must be used inside ChatConversationManagementDialogProvider");
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

export const ChatConversationManagementDialogReducerStore = {
  context: StoreContext,
  useStoreInstance,
  useSelector: useSelector,
  useDispatch: useDispatch,
}
