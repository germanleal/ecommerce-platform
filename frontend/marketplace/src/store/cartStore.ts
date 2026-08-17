import { create } from 'zustand';
export type CartLine = { id: string; productId: string; name: string; quantity: number; unitPrice: number; currency: string };
type CartState = { items: CartLine[]; add: (item: CartLine) => void; remove: (id: string) => void; updateQuantity: (id: string, quantity: number) => void };
export const useCartStore = create<CartState>((set) => ({ items: [], add: (item) => set((s) => ({ items: [...s.items.filter((x) => x.id !== item.id), item] })), remove: (id) => set((s) => ({ items: s.items.filter((x) => x.id !== id) })), updateQuantity: (id, quantity) => set((s) => ({ items: s.items.map((x) => x.id === id ? { ...x, quantity: Math.max(1, quantity) } : x) })) }));
