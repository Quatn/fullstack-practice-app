export interface ChatMessage {
  id: string,
  conversationId: string,
  senderId: string,
  content: string,
  metaData: string,
  createdAt: Date,
  updatedAt: Date,
}

