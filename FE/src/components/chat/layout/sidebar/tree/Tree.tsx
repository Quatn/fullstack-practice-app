import "./css/scrollbar.css"
import type {
  TreeCollection,
  TreeViewExpandedChangeDetails,
  TreeViewRootProps,
} from "@chakra-ui/react";
import { Box, HStack, TreeView } from "@chakra-ui/react";
import ChatSidebarExpandCollapseButton from "./ExpandCollapseButton";
import { ChatSidebarTreeBranchNode, ChatSidebarTreeLeafNode } from "./TreeBranchNode";
import { ChatSidebarReducerStore } from "@/context/chat/chat-sidebar";

interface Node {
  id: string;
  name: string;
  href?: string;
  children?: Node[];
}

export type ChatSidebarTreeNode = Node;

export type ChatSidebarTreeProps = Omit<TreeViewRootProps, "collection"> & {
  expandedValue?: string[];
  onExpandedChange?: (details: TreeViewExpandedChangeDetails<Node>) => void;
  query?: string;
};

export const ChatSidebarTree = (props: ChatSidebarTreeProps) => {
  const { useSelector, useDispatch } = ChatSidebarReducerStore;
  const dispatch = useDispatch();
  const treeCollection = useSelector(s => s.treeCollection);

  if (treeCollection) {
    return (
      <TreeView.Root
        maxW="md"
        collection={treeCollection}
        {...props}
      >
        <HStack justifyContent={"space-between"}>
          <TreeView.Label fontWeight={"bold"}>Menu</TreeView.Label>
          <ChatSidebarExpandCollapseButton />
        </HStack>
        <TreeView.Tree>
          <TreeView.Node
            indentGuide={<TreeView.BranchIndentGuide />}
            render={({ node, nodeState }) =>
              nodeState.isBranch
                ? (
                  <ChatSidebarTreeBranchNode node={node} nodeState={nodeState} query={props.query} />
                )
                : (
                  <ChatSidebarTreeLeafNode node={node} query={props.query} />
                )}
          />
        </TreeView.Tree>
      </TreeView.Root>
    );
  }

  return <Box />
};
