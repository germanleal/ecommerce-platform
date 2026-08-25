import { describe, expect, it } from 'vitest';
import { apiMessage } from '../services/api/client';
import { calculateCartTotals, calculateCartTotalsByCurrency, type CartLine } from '../store/cartStore';

describe('marketplace error handling', () => {
  it('returns a safe message for unexpected failures', () => {
    expect(apiMessage(new Error('database details'))).toBe('Something went wrong. Please try again.');
  });
  it('keeps independent totals when the cart contains multiple currencies', () => {
    const base = { id: '1', productId: '1', storeId: 'store', tenantId: 'tenant', name: 'Product', quantity: 1, netUnitPrice: 100, unitPrice: 119, taxRate: 19 };
    expect(calculateCartTotalsByCurrency([{ ...base, currency: 'USD' }, { ...base, id: '2', productId: '2', currency: 'CLP', netUnitPrice: 1000, unitPrice: 1190 }])).toEqual([
      { net: 100, tax: 19, gross: 119, currency: 'USD' },
      { net: 1000, tax: 190, gross: 1190, currency: 'CLP' }
    ]);
  });
});

describe('cart totals', () => {
  it('calculates net, taxes and gross using quantities', () => {
    const lines: CartLine[] = [
      { id: '1', productId: '1', storeId: 'store', tenantId: 'tenant', name: 'Notebook', quantity: 2, netUnitPrice: 100, unitPrice: 119, taxRate: 19, currency: 'USD' },
      { id: '2', productId: '2', storeId: 'store', tenantId: 'tenant', name: 'Mouse', quantity: 1, netUnitPrice: 20, unitPrice: 23.8, taxRate: 19, currency: 'USD' }
    ];
    expect(calculateCartTotals(lines)).toEqual({ net: 220, tax: 41.8, gross: 261.8, currency: 'USD' });
  });
});
