import { config } from '@/config/config';
import { LocaleMessages } from './intl';

type SupportedLocales = typeof config.LOCALES[number];

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
