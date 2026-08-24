import { useEffect, useState } from 'react';
import { ApplicationShell } from '../layouts/ApplicationShell';
import { Storefront, StoreCard } from '../features/storefront/Storefront';
import { ProductList } from '../features/products/ProductList';
import { apiMessage, orderApi, productApi, storeApi } from '../services/api/client';
import { calculateCartTotalsByCurrency, useCartStore } from '../store/cartStore';
import { useAuthStore } from '../store/authStore';
import { completeLogin, hydrate, login } from '../services/auth/oidc';
import { AdminConsole } from '../features/admin/AdminConsole';
import type { Product, PurchaseOrder, Store } from '../types/marketplace';

function go(path: string) { window.history.pushState({}, '', path); window.dispatchEvent(new PopStateEvent('popstate')); }
function usePath() { const [path, setPath] = useState(window.location.pathname); useEffect(() => { const update = () => setPath(window.location.pathname); window.addEventListener('popstate', update); return () => window.removeEventListener('popstate', update); }, []); return path; }
function SearchBar({ value, onChange }: { value: string; onChange: (value: string) => void }) { return <label className="search"><span>Search</span><input value={value} onChange={(event) => onChange(event.target.value)} placeholder="Search products or stores" /></label>; }
function StoreList() { const [search, setSearch] = useState(''); const query = useQueryStores(search); return <div className="page"><div className="section-heading"><div><p className="eyebrow">Marketplace</p><h1>All stores</h1></div><SearchBar value={search} onChange={setSearch} /></div>{query.loading ? <div className="store-grid"><div className="skeleton" /><div className="skeleton" /></div> : query.error ? <p className="state error" role="alert">{query.error}</p> : !query.data.length ? <p className="state">No stores match your search.</p> : <div className="store-grid">{query.data.map((store) => <StoreCard key={store.id} store={store} onSelect={() => go(`/stores/${store.id}/products`)} />)}</div>}</div>; }
function useQueryStores(search: string) { const [state, setState] = useState<{ loading: boolean; data: Store[]; error?: string }>({ loading: true, data: [] }); useEffect(() => { let active = true; setState({ loading: true, data: [] }); storeApi.list(search).then((response) => active && setState({ loading: false, data: response.data })).catch((error) => active && setState({ loading: false, data: [], error: apiMessage(error) })); return () => { active = false; }; }, [search]); return state; }
function Catalog({ storeId }: { storeId: string }) { const [search, setSearch] = useState(''); return <div className="page"><a href="/stores">← All stores</a><div className="hero compact"><p className="eyebrow">Store catalogue</p><h1>Discover products</h1><p>Browse this store&apos;s latest collection.</p><SearchBar value={search} onChange={setSearch} /></div><ProductList storeId={storeId} search={search} onSelect={(product) => go(`/products/${product.id}`)} /></div>; }
function ProductDetail({ id }: { id: string }) {
  const query = useQueryProduct(id);
  const add = useCartStore((state) => state.add);
  const [notice, setNotice] = useState('');
  if (query.loading) return <div className="page"><p role="status">Loading product…</p></div>;
  if (query.error || !query.data) return <div className="page"><p role="alert" className="state error">{query.error || 'Product not found.'}</p><a href="/stores">Back to stores</a></div>;
  const product = query.data;
  const storeId = String(product.store_id ?? product.storeId ?? '');
  const tenantId = String(product.tenant_id ?? product.tenantId ?? '');
  const netPrice = Number(product.net_price ?? product.amount ?? product.price?.amount ?? 0);
  const grossPrice = Number(product.final_price ?? product.price?.amount ?? 0);
  const taxRate = Number(product.tax_rate ?? 0);
  const currency = String(product.currency ?? product.price?.currency ?? 'USD');
  const image = String(product.image_url ?? product.imageUrl ?? '');
  const addProduct = () => {
    const result = add({ id: product.id, productId: product.id, storeId, tenantId, name: product.name, quantity: 1, netUnitPrice: netPrice, unitPrice: grossPrice, taxRate, currency });
    setNotice(result === 'incremented' ? 'Quantity updated in your cart.' : 'Product added to your cart.');
  };
  return <div className="page detail"><a href={`/stores/${storeId}/products`}>← Back to catalogue</a><div className="detail-grid"><div className="product-image">{image ? <img src={image} alt={product.name} /> : <span aria-hidden="true">{product.name.slice(0, 1)}</span>}</div><div>{product.store_name ? <span className="eyebrow">{String(product.store_name)}</span> : null}<span className="badge">{String(product.category_name ?? product.status)}</span><h1>{product.name}</h1><p>{product.description || 'No description available.'}</p><strong className="price">{grossPrice.toFixed(2)} {currency}</strong><small>{netPrice.toFixed(2)} net + {taxRate.toFixed(2)}% tax</small><br/><button className="button" disabled={product.available === false || !grossPrice} onClick={addProduct}>{product.available === false ? 'Unavailable' : 'Add to cart'}</button>{notice && <p className={notice.startsWith('Finish') || notice.startsWith('A cart') ? 'state error' : 'cart-notice'} role="status">{notice}</p>}</div></div></div>;
}
function useQueryProduct(id: string) { const [state, setState] = useState<{ loading: boolean; data?: Product; error?: string }>({ loading: true }); useEffect(() => { let active = true; productApi.get(id).then((response) => active && setState({ loading: false, data: response.data })).catch((error) => active && setState({ loading: false, error: apiMessage(error) })); return () => { active = false; }; }, [id]); return state; }
function CartPage() {
  const { items, remove, updateQuantity, clear } = useCartStore();
  const accessToken = useAuthStore((state) => state.accessToken);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');
  const [orders, setOrders] = useState<PurchaseOrder[]>([]);
  const totals = calculateCartTotalsByCurrency(items);
  const checkout = async () => {
    if (!accessToken) { await login(); return; }
    setSubmitting(true); setError('');
    try {
      const groups = new Map<string, typeof items>();
      items.forEach((item) => { const key = `${item.tenantId}:${item.storeId}:${item.currency}`; groups.set(key, [...(groups.get(key) ?? []), item]); });
      const responses = await Promise.all([...groups.values()].map((lines) => orderApi.createMarketplace({ tenantId: lines[0].tenantId, storeId: lines[0].storeId, currency: lines[0].currency, lines: lines.map((item) => ({ productId: item.productId, quantity: item.quantity })) })));
      setOrders(responses.map((response) => response.data)); clear();
    } catch (cause) { setError(apiMessage(cause)); }
    finally { setSubmitting(false); }
  };
  if (orders.length) return <div className="page"><p className="eyebrow">Purchase orders created</p><h1>Thank you</h1><p>Your cart was separated into one purchase order per store.</p>{orders.map((order) => <div className="order-confirmation" key={order.id}><span>Order</span><strong>{order.orderNumber}</strong><span>Status</span><strong>{order.status}</strong><span>Total</span><strong>{Number(order.total).toFixed(2)} {order.currency}</strong></div>)}<a className="button" href="/stores">Continue shopping</a></div>;
  return <div className="page"><p className="eyebrow">Your basket</p><h1>Cart</h1>{!items.length ? <p className="state">Your cart is empty. <a href="/stores">Continue shopping</a></p> : <div className="cart-layout"><div className="cart-list">{items.map((item) => <div className="cart-line" key={item.id}><div><strong>{item.name}</strong><p>{item.netUnitPrice.toFixed(2)} net · {item.unitPrice.toFixed(2)} gross {item.currency}</p></div><label>Quantity<input aria-label={`Quantity for ${item.name}`} type="number" min="1" value={item.quantity} onChange={(event) => updateQuantity(item.id, Number(event.target.value))} /></label><strong>{(item.unitPrice * item.quantity).toFixed(2)} {item.currency}</strong><button className="link-button" onClick={() => remove(item.id)}>Remove</button></div>)}</div><aside className="cart-summary"><h2>Order summary</h2>{totals.map((total) => <div className="currency-summary" key={total.currency}><div><span>Net amount</span><strong>{total.net.toFixed(2)} {total.currency}</strong></div><div><span>Taxes</span><strong>{total.tax.toFixed(2)} {total.currency}</strong></div><div className="gross"><span>Gross total</span><strong>{total.gross.toFixed(2)} {total.currency}</strong></div></div>)}<small>Checkout creates one purchase order per store.</small><button className="button" disabled={submitting} onClick={() => checkout().catch(() => setError('Could not start sign-in.'))}>{submitting ? 'Creating orders…' : accessToken ? 'Create purchase orders' : 'Sign in and create orders'}</button><button className="text-button clear-cart" onClick={clear}>Clear cart</button>{error && <p className="error" role="alert">{error}</p>}</aside></div>}</div>;
}
function LoginCallback() { const [error, setError] = useState(''); useEffect(() => { completeLogin().then(go).catch((e: Error) => setError(e.message)); }, []); return <div className="page"><p role="status">{error || 'Completing secure sign-in…'}</p></div>; }
function AdminRoute() { const token = useAuthStore((state) => state.accessToken); const roles = useAuthStore((state) => state.roles); useEffect(() => { hydrate(); }, []); if (!token) return <div className="page"><h1>Sign in required</h1><p className="state">Administration is available only to authenticated users.</p><button className="button" onClick={() => login().catch(() => window.alert('Login is not available.'))}>Sign in</button></div>; if (!roles.some((role) => ['PLATFORM_ADMIN','TENANT_OWNER','TENANT_ADMIN','TENANT_OPERATOR','TENANT_VIEWER'].includes(role))) return <div className="page"><h1>Access denied</h1><p className="state error">Your session does not include an administrative role.</p></div>; return <AdminConsole/>; }
export function App() { const path = usePath(); useEffect(() => { hydrate(); }, []); let content; if (path === '/auth/callback') content = <LoginCallback />; else if (path === '/admin' || path.startsWith('/admin/')) content = <AdminRoute />; else if (path === '/' || path === '') content = <Storefront onSelect={(store) => go(`/stores/${store.id}/products`)} onSelectProduct={(product) => go(`/products/${product.id}`)} />; else if (path === '/stores') content = <StoreList />; else if (path === '/cart') content = <CartPage />; else if (path.startsWith('/products/')) content = <ProductDetail id={path.split('/')[2]} />; else if (path.startsWith('/stores/')) content = <Catalog storeId={path.split('/')[2]} />; else content = <div className="page"><h1>Page not found</h1><a href="/">Return home</a></div>; return <ApplicationShell>{content}</ApplicationShell>; }
