"use client";

import { useAppSelector } from "@/service/hooks";
import { UserState } from "@/types/UserState";
import check from "check-types";

export default function AuthenticatedContent(
  { children, loading, unauthenticatedContent }: {
    children?: React.ReactNode;
    loading?: React.ReactNode;
    unauthenticatedContent?: React.ReactNode;
    // throwErrorAction?: () => Error,
  },
) {
  const isHydrating: boolean = useAppSelector((state) =>
    state.auth.isHydrating
  );

  const isRefreshingToken: boolean = useAppSelector((state) =>
    state.auth.isRefreshingToken
  );

  const userState: UserState | undefined | null = useAppSelector((state) =>
    state.auth.userState
  );

  const accessToken: string | undefined | null = useAppSelector((state) =>
    state.auth.accessToken
  );

  if (isHydrating || isRefreshingToken || check.undefined(userState) || check.undefined(accessToken)) {
    if (loading) {
      return loading
    }
    return <div />
  }

  if (check.null(accessToken) || check.null(userState)) {
    if (unauthenticatedContent) {
      /*
      if (throwErrorAction) {
        throw throwErrorAction()
      }
      */
      return (
        <div>
          {unauthenticatedContent}
        </div>
      );
    }
    return (
      <div />
    )
  }

  return children;
}
