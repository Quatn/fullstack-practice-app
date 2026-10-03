import { PageResponse } from "@/types/DTO/PageResponse";
import { apiSlice } from "./apiSlice";
import { Conversation } from "@/types/chat/Conversation";
import { CONVERSATION_URL } from "../constants";
import { BaseResponse } from "@/types/DTO/BaseResponse";
import { ConversationCreateRequest, ConversationCreateResponse } from "@/types/DTO/chat/conversation/ConversationCreateDTOs";

export const conversationApiSlice = apiSlice.injectEndpoints({
  endpoints: (builder) => ({
    listConversation: builder.query<
      PageResponse<Serialized<Conversation>>,
      object
    >({
      query: () => ({
        url: `${CONVERSATION_URL}/list`,
        method: "GET",
        credentials: "include",
      }),
      providesTags: ["User", "Auth", "Chat", "Conversation"],
    }),

    createConversation: builder.mutation<
      BaseResponse<Serialized<ConversationCreateResponse>>,
      ConversationCreateRequest
    >({
      query: (body) => ({
        url: `${CONVERSATION_URL}/create`,
        method: "POST",
        body,
        credentials: "include",
      }),
      invalidatesTags: (result) => {
        if (result?.success) {
          return ["Conversation"]
        }
        return [];
      },
    }),
  }),
});

export const {
  useListConversationQuery,
  useCreateConversationMutation,
} = conversationApiSlice;
