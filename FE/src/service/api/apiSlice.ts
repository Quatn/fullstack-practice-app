import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { BASE_URL } from "../constants";
import { EndpointBuilder } from "@reduxjs/toolkit/query";
import { AuthState } from "@/types/AuthState";
import { AuthStateError } from "@/lib/errors/AuthStateError";

const baseQuery = fetchBaseQuery({
  baseUrl: BASE_URL, credentials: "include",
  prepareHeaders: (headers, { getState }) => {
    const state = getState() as ({ auth: AuthState });
    const authState = state.auth;

    /*
    if (!authState || authState.hydrating == true) {
      throw new AuthStateError("Auth state undefined or still hydrating");
    }
    */

    const token = authState.accessToken;
    if (!authState.refreshingToken && token) {
      headers.set('Authorization', `Bearer ${token}`);
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
