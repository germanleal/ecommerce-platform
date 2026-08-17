import type { PropsWithChildren } from 'react';
import { useCartStore } from '../store/cartStore';
import { useAuthStore } from '../store/authStore';
import { login, logout } from '../services/auth/oidc';
export function ApplicationShell({ children }: PropsWithChildren) {
  const count = useCartStore((state) => state.items.reduce((total, item) => total + item.quantity, 0));
  const user = useAuthStore((state) => state.user); const roles = useAuthStore((state) => state.roles);
  const canAdmin = roles.some((role) => ['PLATFORM_ADMIN','TENANT_OWNER','TENANT_ADMIN','TENANT_OPERATOR','TENANT_VIEWER'].includes(role));
  return <div className="shell"><header className="topbar"><a className="brand" href="/">Marketplace</a><nav aria-label="Main navigation"><a href="/stores">Stores</a><a href="/cart">Cart ({count})</a>{canAdmin && <a href="/admin">Administration</a>}{user ? <><span className="user-chip">{user.name}</span><button className="text-button" onClick={logout}>Logout</button></> : <button className="text-button" onClick={() => login().catch(() => window.alert('Login is not available.'))}>Login</button>}</nav></header><main>{children}</main><footer className="footer">A simple, secure marketplace experience</footer></div>;
}
