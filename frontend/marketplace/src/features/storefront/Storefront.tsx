import { useQuery } from '@tanstack/react-query';
import { Card } from '../../design-system/Card';
import { apiMessage, storeApi } from '../../services/api/client';
import type { Store } from '../../types/marketplace';
export function StoreCard({ store, onSelect }: { store: Store; onSelect: () => void }) { return <Card><button className="store-button" onClick={onSelect}><div className="store-image">{store.logoUrl ? <img src={store.logoUrl} alt={`${store.name} logo`} /> : <span aria-hidden="true">{store.name.slice(0, 1).toUpperCase()}</span>}</div><div><span className="badge">{store.status}</span><h3>{store.name}</h3><p>{store.description || 'Explore this store and its products.'}</p></div></button></Card>; }
export function Storefront({ onSelect }: { onSelect: (store: Store) => void }) {
  const query = useQuery({ queryKey: ['stores'], queryFn: async () => (await storeApi.list()).data });
  return <div className="page"><section className="hero"><p className="eyebrow">Open marketplace</p><h1>Find something worth keeping.</h1><p>Discover active stores and browse their catalogues in one place.</p><a className="button" href="/stores">Browse stores</a></section><section><div className="section-heading"><h2>Featured stores</h2><a href="/stores">View all</a></div>{query.isLoading ? <div className="store-grid"><div className="skeleton" /><div className="skeleton" /></div> : query.isError ? <p role="alert" className="state error">{apiMessage(query.error)}</p> : !query.data?.length ? <p className="state">No stores are available yet.</p> : <div className="store-grid">{query.data.slice(0, 6).map((store) => <StoreCard key={store.id} store={store} onSelect={() => onSelect(store)} />)}</div>}</section></div>;
}
