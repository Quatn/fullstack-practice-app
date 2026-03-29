"use client"

import { PrimitiveType, useIntl } from "react-intl"
import { LocaleKey } from "./intl"
import check from "check-types"
import { FormatXMLElementFn } from "intl-messageformat"

export function useFormatMessage(): (
  id: LocaleKey, // only accepts valid keys, not any string
  values?: Record<string, PrimitiveType | FormatXMLElementFn<string, string>>,
  placeholder?: string,
) => string {
  const intl = useIntl()

  return (id, values, placeholder) => {
    if (placeholder && !check.string(intl.messages[id])) {
      return placeholder
    }

    return intl.formatMessage({ id }, values)
  }
}
