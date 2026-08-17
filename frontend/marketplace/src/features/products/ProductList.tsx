import { useQuery } from '@tanstack/react-query';
import { Card } from '../../design-system/Card';
import { apiMessage, productApi } from '../../services/api/client';
import type { Product } from '../../types/marketplace';
export function ProductList({ storeId, search, onSelect }: { storeId?: string; search?: string; onSelect?: (product: Product) => void }) {
  const query = useQuery({ queryKey: ['products', storeId, search], queryFn: async () => (await productApi.list({ storeId, search })).data });
  if (query.isLoading) return <div className="product-grid" aria-busy="true"><div className="skeleton" /><div className="skeleton" /><div className="skeleton" /></div>;
  if (query.isError) return <p role="alert" className="state error">{apiMessage(query.error)}</p>;
  if (!query.data?.length) return <p className="state">No products match your search.</p>;
  return <div className="product-grid">{query.data.map((product) => <Card key={product.id}><button className="product-button" onClick={() => onSelect?.(product)}><span className="badge">{product.status}</span><h3>{product.name}</h3><p>{product.description || 'Discover more about this product.'}</p><small>{product.price ? `${product.price.amount.toFixed(2)} ${product.price.currency}` : product.sku}</small></button></Card>)}</div>;
}
