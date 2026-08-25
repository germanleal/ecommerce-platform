import { create } from 'zustand';
import { createJSONStorage, persist } from 'zustand/middleware';

export type CartLine = {
  id: string;
  productId: string;
  storeId: string;
  tenantId: string;
  name: string;
  quantity: number;
  netUnitPrice: number;
  unitPrice: number;
  taxRate: number;
  currency: string;
};

export type CartTotals = { net: number; tax: number; gross: number; currency: string };
export type AddResult = 'added' | 'incremented';

type CartState = {
  items: CartLine[];
  add: (item: CartLine) => AddResult;
  remove: (id: string) => void;
  updateQuantity: (id: string, quantity: number) => void;
  clear: () => void;
};

const cents = (value: number) => Math.round(value * 100);

export function calculateCartTotals(items: CartLine[]): CartTotals {
  const netCents = items.reduce((sum, item) => sum + cents(item.netUnitPrice) * item.quantity, 0);
  const grossCents = items.reduce((sum, item) => sum + cents(item.unitPrice) * item.quantity, 0);
  return {
    net: netCents / 100,
    tax: (grossCents - netCents) / 100,
    gross: grossCents / 100,
    currency: items[0]?.currency ?? 'USD'
  };
}

export function calculateCartTotalsByCurrency(items: CartLine[]): CartTotals[] {
  return [...new Set(items.map((item) => item.currency))].map((currency) =>
    calculateCartTotals(items.filter((item) => item.currency === currency))
  );
}

export const useCartStore = create<CartState>()(persist((set, get) => ({
  items: [],
  add: (item) => {
    const current = get().items;
    const existing = current.find((line) => line.productId === item.productId);
    set({
      items: existing
        ? current.map((line) => line.productId === item.productId ? { ...line, quantity: line.quantity + item.quantity } : line)
        : [...current, item]
    });
    return existing ? 'incremented' : 'added';
  },
  remove: (id) => set((state) => ({ items: state.items.filter((item) => item.id !== id) })),
  updateQuantity: (id, quantity) => set((state) => ({
    items: state.items.map((item) => item.id === id
      ? { ...item, quantity: Math.max(1, Number.isFinite(quantity) ? Math.floor(quantity) : 1) }
      : item)
  })),
  clear: () => set({ items: [] })
}), {
  name: 'marketplace-cart',
  version: 1,
  storage: createJSONStorage(() => localStorage),
  partialize: (state) => ({ items: state.items })
}));
