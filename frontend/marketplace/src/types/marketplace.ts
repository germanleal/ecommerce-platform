export type ProductStatus = 'DRAFT' | 'ACTIVE' | 'INACTIVE' | 'ARCHIVED';
export type Product = { id: string; tenantId: string; storeId: string; catalogId: string; sku: string; name: string; description?: string; status: ProductStatus };
export type Tenant = { id: string; name: string };
