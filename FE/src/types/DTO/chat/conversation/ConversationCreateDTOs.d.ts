import { ConversationType } from "@/constants/enum/conversation-type";

export class ConversationCreateRequest {
  name: string;
  coverUrl: string;
  type: ConversationType;
}

export class ConversationCreateResponse {
  conversation: Conversation;
}
