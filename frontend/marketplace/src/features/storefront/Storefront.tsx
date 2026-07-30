import { Card } from '../../design-system/Card';
import { ProductList } from '../products/ProductList';

export function Storefront() { return <div className="page"><div className="hero"><p className="eyebrow">Tenant storefront</p><h1>Discover the marketplace</h1><p>Explore products from your organization&apos;s active stores.</p></div><Card><h2>Featured products</h2><ProductList /></Card></div>; }
