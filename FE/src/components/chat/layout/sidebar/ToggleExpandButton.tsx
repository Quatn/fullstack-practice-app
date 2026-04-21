import { ResponsiveSidebarLayoutReducerStore } from "@/context/layout/responsive-sidebar-layout";
import { IconButton } from "@chakra-ui/react"
import { LuPanelLeftClose, LuPanelLeftOpen } from "react-icons/lu";

export default function ChatSidebarToggleExpandButton() {
  const { useSelector, useDispatch } = ResponsiveSidebarLayoutReducerStore;
  const dispatch = useDispatch();
  const isExpanded = useSelector(s => s.isExpanded);

  return (
    <IconButton
      onClick={() => {
        dispatch({ type: "TOGGLE_EXPAND" })
      }}
      variant={"outline"}
      aria-label="Toggle sidebar expand"
    >
      {isExpanded ? <LuPanelLeftClose /> : <LuPanelLeftOpen />}
    </IconButton>
  )
}
