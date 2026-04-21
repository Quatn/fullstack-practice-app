import { SidebarTreeNode } from "@/components/chat/layout/sidebar/tree/Tree";
import { ChatSidebarTab } from "@/constants/enum/chat-sidebar-tab";
import { TreeCollection } from "@chakra-ui/react";
import { Store, useStore } from "@tanstack/react-store";
import React, { createContext, useContext } from "react";

interface StoreState {
  menuQuery: string;
  treeCollection: TreeCollection<SidebarTreeNode> | null;
  currentTab: ChatSidebarTab | null;
}

export type ChatSidebarStoreState = StoreState

type StoreAction =
  | { type: "SET_MENU_QUERY"; payload: string }
  | { type: "SET_TREE_COLLECTION_QUERY"; payload: TreeCollection<SidebarTreeNode> | null }
  | { type: "SET_CURRENT_TAB"; payload: ChatSidebarTab | null }
  | { type: "RESET" };

const initialState: StoreState = {
  menuQuery: "",
  treeCollection: null,
  currentTab: null,
};

function reducer(state: StoreState, action: StoreAction): StoreState {
  switch (action.type) {
    case "SET_MENU_QUERY":
      return { ...state, menuQuery: action.payload };
    case "SET_TREE_COLLECTION_QUERY":
      return { ...state, treeCollection: action.payload };
    case "SET_CURRENT_TAB":
      return { ...state, currentTab: action.payload };
    case "RESET":
      return initialState;
    default:
      return state;
  }
}

const StoreContext = createContext<Store<StoreState> | null>(null);

export function ChatSidebarProvider(
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
  if (!store) throw new Error("ChatSidebarReducerStore functions must be used inside ChatSidebarProvider");
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

export const ChatSidebarReducerStore = {
  context: StoreContext,
  useStoreInstance,
  useSelector: useSelector,
  useDispatch: useDispatch,
}
