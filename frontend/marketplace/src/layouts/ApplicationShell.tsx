import { useEffect, useState, type PropsWithChildren } from 'react';
import { useCartStore } from '../store/cartStore';
import { useAuthStore } from '../store/authStore';
import { login, logout } from '../services/auth/oidc';

type Theme = 'light' | 'dark';
const preferredTheme = (): Theme => localStorage.getItem('marketplace-theme') === 'light' || localStorage.getItem('marketplace-theme') === 'dark' ? localStorage.getItem('marketplace-theme') as Theme : window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';

export function ApplicationShell({ children }: PropsWithChildren) {
  const count = useCartStore((state) => state.items.reduce((total, item) => total + item.quantity, 0));
  const user = useAuthStore((state) => state.user);
  const roles = useAuthStore((state) => state.roles);
  const [theme, setTheme] = useState<Theme>(preferredTheme);
  const canAdmin = roles.some((role) => ['PLATFORM_ADMIN', 'TENANT_OWNER', 'TENANT_ADMIN', 'TENANT_OPERATOR', 'TENANT_VIEWER'].includes(role));
  useEffect(() => { document.documentElement.dataset.theme = theme; localStorage.setItem('marketplace-theme', theme); }, [theme]);
  const toggleTheme = () => setTheme((current) => current === 'light' ? 'dark' : 'light');
  return <div className="shell"><header className="topbar"><a className="brand" href="/">Marketplace</a><nav aria-label="Main navigation"><a href="/stores">Stores</a><a href="/cart">Cart ({count})</a>{canAdmin && <a href="/admin">Administration</a>}<button className="theme-toggle" onClick={toggleTheme} aria-label={`Switch to ${theme === 'light' ? 'dark' : 'light'} theme`} aria-pressed={theme === 'dark'}><span aria-hidden="true">{theme === 'light' ? '☾' : '☀'}</span><span>{theme === 'light' ? 'Dark' : 'Light'}</span></button>{user ? <><span className="user-chip">{user.name}</span><button className="text-button" onClick={logout}>Logout</button></> : <button className="text-button" onClick={() => login().catch(() => window.alert('Login is not available.'))}>Login</button>}</nav></header><main>{children}</main><footer className="footer">A simple, secure marketplace experience</footer></div>;
}
