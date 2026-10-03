"use client"

import { ConversationType } from "@/constants/enum/conversation-type";
import { ChatConversationManagementDialogReducerStore } from "@/context/chat/dialog/conversation-management-dialog";
import { Button, CloseButton, Dialog, Portal } from "@chakra-ui/react"
import ConversationCreateForm from "./Form";

type CreateConversationFormFields = {
  name: string;
  coverUrl: string;
  type: ConversationType;
  createdAt: Date;
  updatedAt: Date;
}

export default function ConversationManagementDialog() {
  const { useSelector, useDispatch } = ChatConversationManagementDialogReducerStore;
  const dispatch = useDispatch();
  const isOpen = useSelector(s => s.isOpen);

  const setIsOpen = (value: boolean) => {
    dispatch({ type: "SET_IS_OPEN", payload: value })
  }

  return (
    <Dialog.Root lazyMount open={isOpen} onOpenChange={(e) => setIsOpen(e.open)}>
      <Portal>
        <Dialog.Backdrop />
        <Dialog.Positioner>
          <Dialog.Content>
            <Dialog.Header>
              <Dialog.Title>Dialog Title</Dialog.Title>
            </Dialog.Header>
            <Dialog.Body>
              <ConversationCreateForm />
            </Dialog.Body>
            <Dialog.Footer>
              <Dialog.ActionTrigger asChild>
                <Button variant="outline">Cancel</Button>
              </Dialog.ActionTrigger>
              <Button>Save</Button>
            </Dialog.Footer>
            <Dialog.CloseTrigger asChild>
              <CloseButton size="sm" />
            </Dialog.CloseTrigger>
          </Dialog.Content>
        </Dialog.Positioner>
      </Portal>
    </Dialog.Root>
  )
}
