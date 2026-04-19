"use client";
import ClientSideErrorWarning from "@/components/layout/ClientSideErrorWarning";
import { useEffect } from "react";

export default function RootLayoutError({
  error,
  reset,
}: {
  error: Error & { digest?: string }
  reset: () => void
}) {
  useEffect(() => {
    console.log(error)
  }, [error])

  return <ClientSideErrorWarning reset={reset} position={"fixed"} w={"100vw"} h={"100vh"} top={0} left={0} />;
}
