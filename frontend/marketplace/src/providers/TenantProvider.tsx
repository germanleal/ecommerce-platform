import { createContext, useContext, useEffect, useMemo, useState, type PropsWithChildren } from 'react';
import { adminApi, type AdminTenant } from '../services/api/client';
import { useAuthStore } from '../store/authStore';

type TenantContextValue = { tenants: AdminTenant[]; activeTenant: AdminTenant | null; selectTenant: (id: string) => void; loading: boolean };
const TenantContext = createContext<TenantContextValue>({ tenants: [], activeTenant: null, selectTenant: () => undefined, loading: false });
export function TenantProvider({ children }: PropsWithChildren) {
  const authenticated = useAuthStore((state) => Boolean(state.accessToken));
  const [tenants, setTenants] = useState<AdminTenant[]>([]); const [activeId, setActiveId] = useState(sessionStorage.getItem('active_tenant')); const [loading, setLoading] = useState(false);
  useEffect(() => { if (!authenticated) { setTenants([]); return; } setLoading(true); adminApi.tenants().then(({ data }) => { setTenants(data); if (!activeId && data[0]) setActiveId(data[0].id); }).catch(() => setTenants([])).finally(() => setLoading(false)); }, [authenticated]);
  const value = useMemo(() => ({ tenants, activeTenant: tenants.find((tenant) => tenant.id === activeId) ?? tenants[0] ?? null, selectTenant: (id: string) => { sessionStorage.setItem('active_tenant', id); setActiveId(id); }, loading }), [tenants, activeId, loading]);
  return <TenantContext.Provider value={value}>{children}</TenantContext.Provider>;
}
export function useTenant() { return useContext(TenantContext); }
