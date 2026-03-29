"use client"

import * as React from 'react'
import { IntlProvider as IntlProvider_ } from 'react-intl'

// "import type" ensures en messages aren't bundled by default
import type sourceOfTruth from '@/dictionaries/en.json'
import { config } from '@/config/config'

export type LocaleMessages = typeof sourceOfTruth
export type LocaleKey = keyof LocaleMessages

type SupportedLocales = typeof config.LOCALES[number];

// return type on this signature enforces that all languages have the same translations defined
export function importMessages(
  locale: SupportedLocales
): Promise<LocaleMessages | null> {
  switch (locale) {
    case 'en-US':
      return import('@/dictionaries/en.json') as Promise<LocaleMessages>
    case 'vi':
      return import('@/dictionaries/vi.json') as Promise<LocaleMessages>
    default:
      return Promise.resolve(null);
  }
}

export type IntlProviderProps = Omit<React.ComponentProps<typeof IntlProvider_>, 'messages'> & {
  messages: LocaleMessages | Record<string, string>
}

export const IntlProvider: React.FC<IntlProviderProps> = props => {
  return (
    <IntlProvider_ {...props} />
  )
}
