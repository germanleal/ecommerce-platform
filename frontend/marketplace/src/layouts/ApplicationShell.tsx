import type { PropsWithChildren } from 'react';
export function ApplicationShell({ children }: PropsWithChildren) { return <div className="shell"><header className="topbar"><strong>Marketplace</strong><nav aria-label="Main navigation"><a href="#catalog">Catalog</a><a href="#stores">Stores</a></nav></header><main>{children}</main></div>; }
