"use client"

import * as React from 'react'
import { IntlProvider as IntlProvider_ } from 'react-intl'

// "import type" ensures en messages aren't bundled by default
import sourceOfTruth from '@/dictionaries/en.json'
// ensure that all errors defined in the generated ErrorCode is defined by the dictionaries
sourceOfTruth satisfies Record<ErrorCode, string>

import { config } from '@/config/config'
import { ErrorCode } from '@/generated/ErrorCode'


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
