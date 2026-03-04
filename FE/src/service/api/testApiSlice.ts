import { BaseResponse } from "@/types/DTO/BaseResponse";
import { BASE_URL } from "../constants";
import { apiSlice } from "./apiSlice";

export const testApiSlice = apiSlice.injectEndpoints({
  endpoints: (builder) => ({
    healthCheck: builder.query<
      BaseResponse<string>,
      void
    >({
      query: (body) => ({
        url: `${BASE_URL}/user/dev/getall`,
        method: "POST",
        body,
        credentials: "include",
      }),
      providesTags: ["User"]
    }),
  }),
});

export const {
  useHealthCheckQuery
} = testApiSlice;
