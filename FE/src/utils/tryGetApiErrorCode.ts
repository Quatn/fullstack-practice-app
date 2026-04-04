import { SerializedError } from "@reduxjs/toolkit";
import { FetchBaseQueryError } from "@reduxjs/toolkit/query";
import check from "check-types";

export const tryGetApiErrorCode = (error?: Error | FetchBaseQueryError | SerializedError | unknown) => {
  if (!check.object(error)) {
    return undefined;
  }

  try {
    const dataError = (error as { data?: { error?: string } }).data?.error
    if (dataError) {
      return dataError
    }

    const dataDataError = (error as { data?: { data?: { error?: string } } }).data?.data?.error
    if (dataDataError) {
      return dataDataError
    }
  }
  catch {
    return undefined;
  }
}

