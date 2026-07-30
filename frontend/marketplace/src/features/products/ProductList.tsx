import { useQuery } from '@tanstack/react-query';
import { Card } from '../../design-system/Card';
import { productApi } from '../../services/api/client';
import type { Product } from '../../types/marketplace';

export function ProductList() { const query = useQuery({ queryKey: ['products'], queryFn: async () => (await productApi.list()).data as Product[] }); if (query.isLoading) return <p role="status">Loading products…</p>; if (query.isError) return <p role="alert">Products are temporarily unavailable.</p>; if (!query.data?.length) return <p>No products available yet.</p>; return <div className="product-grid">{query.data.map((product) => <Card key={product.id}><span className="badge">{product.status}</span><h3>{product.name}</h3><p>{product.description}</p><small>{product.sku}</small></Card>)}</div>; }
