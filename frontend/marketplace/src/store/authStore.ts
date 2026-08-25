import { create } from 'zustand';

const storage = typeof window === 'undefined' ? null : window.sessionStorage;

export type SessionUser = { id: string; name: string; tenantId?: string };
type AuthState = {
  user: SessionUser | null;
  roles: string[];
  permissions: string[];
  accessToken: string | null;
  setSession: (user: SessionUser, accessToken: string, roles: string[], permissions?: string[]) => void;
  clearAccessToken: () => void;
  logout: () => void;
};
export const useAuthStore = create<AuthState>((set) => ({
  user: null, roles: [], permissions: [], accessToken: storage?.getItem('access_token') ?? null,
  setSession: (user, accessToken, roles, permissions = []) => { storage?.setItem('access_token', accessToken); set({ user, accessToken, roles, permissions }); },
  clearAccessToken: () => { storage?.removeItem('access_token'); set({ user: null, accessToken: null, roles: [], permissions: [] }); },
  logout: () => { storage?.removeItem('access_token'); storage?.removeItem('refresh_token'); storage?.removeItem('id_token'); set({ user: null, accessToken: null, roles: [], permissions: [] }); }
}));
