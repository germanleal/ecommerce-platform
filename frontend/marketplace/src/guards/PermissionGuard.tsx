import type { PropsWithChildren } from 'react';
import { useAuthStore } from '../store/authStore';

export function PermissionGuard({ permission, children }: PropsWithChildren<{ permission: string }>) { const allowed = useAuthStore((state) => state.permissions.includes(permission)); return allowed ? <>{children}</> : null; }
