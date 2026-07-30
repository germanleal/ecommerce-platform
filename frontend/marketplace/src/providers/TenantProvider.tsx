import { createContext, useContext, useMemo, type PropsWithChildren } from 'react';
import type { Tenant } from '../types/marketplace';

const TenantContext = createContext<Tenant | null>(null);
export function TenantProvider({ children }: PropsWithChildren) { const tenant = useMemo<Tenant | null>(() => null, []); return <TenantContext.Provider value={tenant}>{children}</TenantContext.Provider>; }
export function useTenant() { return useContext(TenantContext); }
