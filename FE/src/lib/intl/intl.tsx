"use client"

import * as React from 'react'
import { IntlProvider as IntlProvider_ } from 'react-intl'
import { ErrorCode } from '@/generated/ErrorCode'

// "import type" ensures en messages aren't bundled by default
import sourceOfTruth from '@/dictionaries/en.json'
// ensure that all errors defined in the generated ErrorCode is defined by the dictionaries
sourceOfTruth satisfies Record<ErrorCode, string>


export type LocaleMessages = typeof sourceOfTruth
export type LocaleKey = keyof LocaleMessages

export type IntlProviderProps = Omit<React.ComponentProps<typeof IntlProvider_>, 'messages'> & {
  messages: LocaleMessages | Record<string, string>
}

export const IntlProvider: React.FC<IntlProviderProps> = props => {
  return (
    <IntlProvider_ {...props} />
  )
}
