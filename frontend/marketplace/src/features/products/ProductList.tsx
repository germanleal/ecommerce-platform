import { useQuery } from '@tanstack/react-query';
import { Card } from '../../design-system/Card';
import { apiMessage, productApi } from '../../services/api/client';
import type { Product } from '../../types/marketplace';
export function ProductCard({ product, onSelect }: { product: Product; onSelect?: (product: Product) => void }) {
  const price = product.final_price ?? product.price?.amount;
  const currency = product.currency ?? product.price?.currency;
  const image = product.image_url ?? product.imageUrl;
  return <Card><button className="product-button" onClick={() => onSelect?.(product)}>
    <div className="product-image">{image ? <img src={image} alt={product.name} /> : <span aria-hidden="true">{product.name.slice(0, 1)}</span>}</div>
    <div><span className="badge">{product.category_name ?? product.status}</span><h3>{product.name}</h3><p>{product.description || 'Discover more about this product.'}</p><strong className="price">{price != null ? `${Number(price).toFixed(2)} ${currency}` : product.sku}</strong></div>
  </button></Card>;
}
export function ProductList({ storeId, search, onSelect }: { storeId?: string; search?: string; onSelect?: (product: Product) => void }) {
  const query = useQuery({ queryKey: ['products', storeId, search], queryFn: async () => (await productApi.list({ storeId, search })).data });
  if (query.isLoading) return <div className="product-grid" aria-busy="true"><div className="skeleton" /><div className="skeleton" /><div className="skeleton" /></div>;
  if (query.isError) return <p role="alert" className="state error">{apiMessage(query.error)}</p>;
  if (!query.data?.length) return <p className="state">No products match your search.</p>;
  return <div className="product-grid">{query.data.map((product) => <ProductCard key={product.id} product={product} onSelect={onSelect} />)}</div>;
}
