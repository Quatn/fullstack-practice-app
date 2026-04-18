import { apiSlice } from "./apiSlice";
import { AUTH_URL } from "../constants";
import { LoginRequest, LoginResponse } from "@/types/DTO/auth/LoginDTOs";
import { LogoutRequest, LogoutResponse } from "@/types/DTO/auth/LogoutDTOs";
import { RegisterRequest, RegisterResponse } from "@/types/DTO/auth/RegisterDTOs";
import { TokenRefreshRequest, TokenRefreshResponse } from "@/types/DTO/auth/TokenRefreshDTOs";

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
      invalidatesTags: (result) => {
        if (result?.success) {
          return ["User", "Auth"]
        }
        return [];
      },
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
      invalidatesTags: (result) => {
        if (result?.success) {
          return ["User", "Auth"]
        }
        return [];
      },
    }),

    register: builder.mutation<
      RegisterResponse,
      RegisterRequest
    >({
      query: (body) => ({
        url: `${AUTH_URL}/register`,
        method: "POST",
        body,
        credentials: "include",
      }),
      invalidatesTags: (result) => {
        if (result?.success) {
          return ["User", "Auth"]
        }
        return [];
      },
    }),

    tokenRefresh: builder.query<
      TokenRefreshResponse,
      TokenRefreshRequest
    >({
      query: () => ({
        url: `${AUTH_URL}/token/refresh`,
        method: "GET",
        credentials: "include",
      }),
      providesTags: ["User", "Auth"],
    }),
  }),
});

export const {
  useLoginMutation,
  useLogoutMutation,
  useRegisterMutation,
  useTokenRefreshQuery,
} = authApiSlice;
