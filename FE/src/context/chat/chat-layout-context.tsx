import { Store, useStore } from "@tanstack/react-store";
import React, { createContext, useContext } from "react";

interface StoreState {
  isDesktop: boolean;
  isDirty: boolean;
  isManuallyExpanded: boolean;
  isExpanded: boolean;
}

type StoreAction =
  | { type: "SET_IS_DESKTOP"; payload: boolean }
  | { type: "SET_IS_DIRTY"; payload: boolean }
  | { type: "SET_IS_MANUALLY_EXPANDED"; payload: boolean }
  | { type: "SET_IS_EXPANDED"; payload: boolean }
  | { type: "RESET" };

const initialState: StoreState = {
  isDesktop: true,
  isDirty: false,
  isManuallyExpanded: true,
  isExpanded: true,
};

function shouldExpand(state: StoreState) {
  if (state.isDirty) {
    return state.isManuallyExpanded;
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
    case "SET_IS_MANUALLY_EXPANDED": {
      const newState: StoreState = { ...state, isManuallyExpanded: action.payload }
      return { ...newState, isExpanded: shouldExpand(newState) };
    }
    case "SET_IS_EXPANDED":
      return { ...state, isExpanded: action.payload };
    case "RESET":
      return initialState;
    default:
      return state;
  }
}

const StoreContext = createContext<Store<StoreState> | null>(null);

export function ChatLayoutProvider(
  { children }: { children: React.ReactNode },
) {
  const [store] = React.useState(
    () => new Store<StoreState>(initialState)
  );

  return (
    <StoreContext.Provider value={store}>
      {children}
    </StoreContext.Provider>
  );
}

// Internal hook to get the store
function useStoreInstance() {
  const store = useContext(StoreContext);
  if (!store) throw new Error("ChatLayoutReducerStore functions must be used inside ChatLayoutProvider");
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

export const ChatLayoutReducerStore = {
  context: StoreContext,
  useStoreInstance,
  useSelector: useSelector,
  useDispatch: useDispatch,
}
