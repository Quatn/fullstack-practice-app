"use client"

import { Store, useStore } from "@tanstack/react-store";
import React, { createContext, useContext } from "react";

interface StoreState {
  isDesktop: boolean;
  isDirty: boolean;
  isExpanded: boolean;
}

export type ResponsiveSidebarLayoutStoreState = StoreState

type StoreAction =
  | { type: "SET_IS_DESKTOP"; payload: boolean }
  | { type: "SET_IS_DIRTY"; payload: boolean }
  | { type: "SET_IS_EXPANDED"; payload: boolean }
  | { type: "TOGGLE_EXPAND" }
  | { type: "RESET" };

const initialState: StoreState = {
  isDesktop: false,
  isDirty: false,
  isExpanded: false,
};

function shouldExpand(state: StoreState) {
  if (state.isDirty) {
    return state.isExpanded;
  }
  return !!state.isDesktop;
}

function reducer(state: StoreState, action: StoreAction): StoreState {
  switch (action.type) {
    case "SET_IS_DESKTOP": {
      const newState: StoreState = { ...state, isDesktop: action.payload }
      return { ...newState, isExpanded: shouldExpand(newState) };
    }
    case "SET_IS_DIRTY": {
      const newState: StoreState = { ...state, isDirty: action.payload }
      return { ...newState, isExpanded: shouldExpand(newState) };
    }
    case "SET_IS_EXPANDED":
      return { ...state, isExpanded: action.payload, isDirty: true };
    case "TOGGLE_EXPAND":
      return { ...state, isExpanded: !state.isExpanded, isDirty: true };
    case "RESET":
      return initialState;
    default:
      return state;
  }
}

const StoreContext = createContext<Store<StoreState> | null>(null);

export function ResponsiveSidebarLayoutProvider(
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
  if (!store) throw new Error("ResponsiveSidebarLayoutReducerStore functions must be used inside ResponsiveSidebarLayoutProvider");
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

export const ResponsiveSidebarLayoutReducerStore = {
  context: StoreContext,
  useStoreInstance,
  useSelector: useSelector,
  useDispatch: useDispatch,
}
