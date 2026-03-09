import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { BASE_URL } from "../constants";
import { EndpointBuilder } from "@reduxjs/toolkit/query";
import { AuthState } from "@/types/AuthState";

const baseQuery = fetchBaseQuery({
  baseUrl: BASE_URL, credentials: "include",
  prepareHeaders: (headers, { getState }) => {
    const authState = getState() as (AuthState & { hydrating: boolean });

    if (authState && authState.hydrating == false) {
      const token = authState.accessToken;
      if (token) {
        headers.set('Authorization', `Bearer ${token}`);
      }
    }

    return headers;
  },
});


const tagTypes = [
  "User",
  "Auth",
];

export const apiSlice = createApi({
  baseQuery,
  tagTypes,
  endpoints: () => ({}),
});

export type ApiBaseQuery = typeof baseQuery;

export type ApiBuilder = EndpointBuilder<
  ApiBaseQuery,
  typeof tagTypes[number],
  "api"
>;
