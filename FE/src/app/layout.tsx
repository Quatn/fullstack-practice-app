import { ReactNode } from 'react';

type Props = {
  children: ReactNode;
};

// Since the proxy file redirects requests to include locale, 
// this root only serves the exceptions to normal operations. 
// The "real" application root layout is at ./[locale]/layout.tsx
export default function RootLayout({ children }: Props) {
  return children;
}
