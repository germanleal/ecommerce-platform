import type { PropsWithChildren } from 'react';
import { useAuthStore } from '../store/authStore';

export function RoleGuard({ roles, children }: PropsWithChildren<{ roles: string[] }>) { const current = useAuthStore((state) => state.roles); return roles.some((role) => current.includes(role)) ? <>{children}</> : null; }
