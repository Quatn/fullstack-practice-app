import { apiSlice } from "./apiSlice";
import { AUTH_URL } from "../constants";
import { LoginRequest, LoginResponse } from "@/types/DTO/auth/LoginDTOs";
import { LogoutRequest, LogoutResponse } from "@/types/DTO/auth/LogoutDTOs";

export const authApiSlice = apiSlice.injectEndpoints({
  endpoints: (builder) => ({
    login: builder.mutation<
      LoginResponse,
      LoginRequest
    >({
      query: (body) => ({
        url: `${AUTH_URL}/login`,
        method: "POST",
        body,
        credentials: "include",
      }),
      invalidatesTags: ["User", "Auth"],
    }),

    logout: builder.mutation<
      LogoutResponse,
      LogoutRequest
    >({
      query: (body) => ({
        url: `${AUTH_URL}/logout`,
        method: "POST",
        body,
        credentials: "include",
      }),
      invalidatesTags: ["User", "Auth"],
    }),
  }),
});

export const {
  useLoginMutation,
  useLogoutMutation,
} = authApiSlice;
