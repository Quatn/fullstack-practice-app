import { AuthState } from "@/types/AuthState";
import { devlog } from "@/utils/devlog";
import { createSlice } from "@reduxjs/toolkit";
import check from "check-types";

const initialState: AuthState = {
  userState: null,
  accessToken: null,
  hydrating: true,
  refreshingToken: true,
};

const authSlice = createSlice({
  name: "auth",
  initialState: initialState,
  reducers: {
    hydrate: (state) => {
      try {
        const localUserState = localStorage.getItem("userState");
        if (check.string(localUserState)) {
          state.userState = JSON.parse(localUserState);
        }
        // state.accessToken = localStorage.getItem("accessToken");
      } catch (e) {
        devlog(e);
        localStorage.removeItem("userState");
      }
      finally {
        state.hydrating = false
      }
    },
    setCredentials: (state, action) => {
      state.userState = action.payload;
      localStorage.setItem("userState", JSON.stringify(action.payload));
      const expirationTime = new Date().getTime() + 30 * 24 * 60 * 60 * 1000; // 30 days
      localStorage.setItem("expirationTime", expirationTime.toString());
    },
    clearCredentials: (state) => {
      state.userState = null;
      localStorage.removeItem("userState");
      localStorage.removeItem("expirationTime");
    },
    setAccessToken: (state, action) => {
      state.accessToken = action.payload;
    },
    clearAccessToken: (state) => {
      state.accessToken = null;
    },
    setRefreshingToken: (state, action) => {
      state.refreshingToken = action.payload;
    },
  },
});

export const { hydrate, setCredentials, clearCredentials, setAccessToken, clearAccessToken, setRefreshingToken } = authSlice.actions;

export default authSlice.reducer;
