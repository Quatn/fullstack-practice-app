"use client"

import { PrimitiveType, useIntl } from "react-intl";
import { LocaleKey } from "./intl";
import { FormatXMLElementFn } from "intl-messageformat";
import check from "check-types";
import { Text, TextProps } from "@chakra-ui/react";

export type FormattedTextProps = {
  id: LocaleKey,
  values?: Record<string, PrimitiveType | FormatXMLElementFn<string, string>>,
  placeholder?: React.ReactNode,
} & Omit<TextProps, "id" | "values" | "placeholder">

export const FormattedText = ({ id, values = {}, placeholder, ...rest }: FormattedTextProps) => {
  const intl = useIntl()

  if (placeholder && !check.string(intl.messages[id])) {
    return placeholder
  }

  return <Text {...rest}>{intl.formatMessage({ id }, values)}</Text>;
}
