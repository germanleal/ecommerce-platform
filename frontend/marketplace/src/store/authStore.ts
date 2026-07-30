import { create } from 'zustand';

type AuthState = { user: { id: string; name: string } | null; roles: string[]; permissions: string[]; setUser: (user: AuthState['user']) => void; logout: () => void };
export const useAuthStore = create<AuthState>((set) => ({ user: null, roles: [], permissions: [], setUser: (user) => set({ user }), logout: () => set({ user: null, roles: [], permissions: [] }) }));
