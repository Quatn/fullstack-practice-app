"use client"

import { ChatSidebarReducerStore } from "@/context/chat/chat-sidebar";
import { ResponsiveSidebarLayoutReducerStore } from "@/context/layout/responsive-sidebar-layout";
import { HStack, IconButton, Input } from "@chakra-ui/react";
import { ChangeEvent, useEffect, useState } from "react";
import { LuSearch } from "react-icons/lu";

export default function ChatSidebarSearchBar() {
  const { useSelector, useDispatch } = ChatSidebarReducerStore;
  const dispatch = useDispatch();
  const menuQuery = useSelector(s => s.menuQuery);

  const { useSelector: useLayoutSelector, useDispatch: useLayoutDispatch } = ResponsiveSidebarLayoutReducerStore;
  const layoutDispatch = useLayoutDispatch();
  const isExpanded = useLayoutSelector(s => s.isExpanded);

  const [localSearch, setLocalSearch] = useState(menuQuery);

  useEffect(() => {
    const timeout = setTimeout(() => {
      dispatch({ type: "SET_MENU_QUERY", payload: localSearch });
    }, 300); // 300ms debounce

    return () => clearTimeout(timeout);
  }, [localSearch, dispatch]);

  const changeQuery = (e: ChangeEvent<HTMLInputElement>) => {
    setLocalSearch(e.currentTarget.value);
  };

  return (
    <IconButton
      onClick={() => {
        if (!isExpanded) {
          layoutDispatch({ type: "TOGGLE_EXPAND" })
        }
      }}
      variant={"outline"}
      aria-label="Toggle sidebar expand"
      w="full"
    >
      <HStack justifyContent={"start"} w="full" px={2}>
        {<LuSearch />}{isExpanded && <Input placeholder="Menu" value={localSearch} variant={"flushed"} onChange={changeQuery} />}
      </HStack>
    </IconButton>

  )
}
