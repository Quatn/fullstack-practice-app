import { config } from "@/config/config";
import { importMessages } from "@/lib/intl/importMessages";
import { LocaleKey } from "@/lib/intl/intl";
import { FormatXMLElementFn } from "intl-messageformat";
import { PrimitiveType } from "react-intl";
import { createIntl, createIntlCache } from 'react-intl/server'

type FormattedProp = {
  default?: string,
  id?: LocaleKey,
  values?: Record<string, PrimitiveType | FormatXMLElementFn<string, string>>,
}

export type CreateGenerateMetadataFuncProps = {
  title?: FormattedProp,
  description?: FormattedProp,
}

export const createGenerateMetadataFunc = (props: CreateGenerateMetadataFuncProps) => {
  return async function generateMetadata({
    params,
  }: Readonly<{
    params: Promise<{ locale: typeof config.LOCALES[number] }>;
  }>) {
    let title = props.title?.default;
    let description = props.description?.default;

    if (props.title?.id || props.description?.id) {
      const { locale } = await params;

      const cache = createIntlCache()
      const messages = await importMessages(locale);
      const intl = createIntl({ locale, messages: messages ?? {} }, cache)

      if (props.title?.id) {
        title = intl.formatMessage({ id: props.title.id }, props.title?.values)
      }

      if (props.description?.id) {
        description = intl.formatMessage({ id: props.description.id }, props.description?.values)
      }
    }

    return {
      title,
      description,
    }
  }
}
