import { config } from '@/config/config';
import { redirect } from 'next/navigation';

// This page only renders when the app is built statically
export default function RootPage() {
  redirect(config.DEFAULT_LOCALE);
}
