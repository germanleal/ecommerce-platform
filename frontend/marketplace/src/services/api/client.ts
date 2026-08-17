import axios from 'axios';
import type { Product, Store } from '../../types/marketplace';
import { useAuthStore } from '../../store/authStore';

export const apiClient = axios.create({ baseURL: import.meta.env.VITE_API_URL ?? '/api', timeout: 10000 });
apiClient.interceptors.request.use((config) => {
  config.headers = config.headers ?? {};
  config.headers['X-Correlation-Id'] ??= crypto.randomUUID();
  const token = useAuthStore.getState().accessToken;
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});
apiClient.interceptors.response.use((response) => response, (error) => { if (axios.isAxiosError(error) && error.response?.status === 401) useAuthStore.getState().logout(); return Promise.reject(error); });
export function apiMessage(error: unknown): string {
  if (!axios.isAxiosError(error)) return 'Something went wrong. Please try again.';
  if (error.response?.status === 401) return 'Please sign in to continue.';
  if (error.response?.status === 403) return 'You do not have access to this resource.';
  if (error.response?.status === 404) return 'We could not find that resource.';
  if (error.response?.status === 409) return 'This action conflicts with the current state.';
  if (error.code === 'ECONNABORTED') return 'The request timed out. Please try again.';
  return 'The marketplace is temporarily unavailable.';
}
export const storeApi = { list: (search = '') => apiClient.get<Store[]>('/stores', { params: search ? { search } : undefined }), get: (id: string) => apiClient.get<Store>(`/stores/${id}`) };
export const productApi = { list: (params?: { storeId?: string; categoryId?: string; search?: string }) => apiClient.get<Product[]>('/products', { params }), get: (id: string) => apiClient.get<Product>(`/products/${id}`), publish: (id: string) => apiClient.post(`/products/${id}/publish`) };

export type Organization = { id: string; name: string; slug: string; status: string; created_at?: string; updated_at?: string };
export type AdminTenant = { id: string; organization_id: string; name: string; slug: string; status: string; created_at?: string; updated_at?: string; configuration?: Record<string, unknown> };
export type Membership = { id: string; user_id: string; tenant_id: string; role: string; status: string; created_at?: string };
export type AuditRecord = { id: string; tenant_id?: string; actor_id?: string; action: string; resource_type: string; resource_id?: string; result: string; correlation_id?: string; created_at: string };
const adminClient = axios.create({ baseURL: import.meta.env.VITE_ADMIN_API_URL ?? '/admin-api', timeout: 10000 });
adminClient.interceptors.request.use((config) => { config.headers = config.headers ?? {}; config.headers['X-Correlation-Id'] ??= crypto.randomUUID(); const token = useAuthStore.getState().accessToken; if (token) config.headers.Authorization = `Bearer ${token}`; return config; });
adminClient.interceptors.response.use((response) => response, (error) => { if (axios.isAxiosError(error) && error.response?.status === 401) useAuthStore.getState().logout(); return Promise.reject(error); });
const integrationsClient = axios.create({ baseURL: import.meta.env.VITE_INTEGRATION_API_URL ?? '/integration-api', timeout: 10000 });
integrationsClient.interceptors.request.use((config) => { config.headers = config.headers ?? {}; const token = useAuthStore.getState().accessToken; if (token) config.headers.Authorization = `Bearer ${token}`; config.headers['X-Correlation-Id'] ??= crypto.randomUUID(); return config; });
const analyticsClient = axios.create({ baseURL: import.meta.env.VITE_ANALYTICS_API_URL ?? '/analytics-api', timeout: 10000 });
analyticsClient.interceptors.request.use((config) => { config.headers = config.headers ?? {}; const token = useAuthStore.getState().accessToken; if (token) config.headers.Authorization = `Bearer ${token}`; return config; });
export const adminApi = {
  organizations: () => adminClient.get<Organization[]>('/organizations'), createOrganization: (body: { name: string; slug: string }) => adminClient.post<Organization>('/organizations', body), updateOrganization: (id: string, body: { name: string; slug: string }) => adminClient.put<Organization>(`/organizations/${id}`, body),
  tenants: () => adminClient.get<AdminTenant[]>('/tenants'), tenant: (id: string) => adminClient.get<AdminTenant>(`/tenants/${id}`), createTenant: (body: { organizationId: string; name: string; slug: string; ownerId?: string; configuration: Record<string, unknown> }) => adminClient.post<AdminTenant>('/tenants', body), updateTenant: (id: string, body: { name: string; slug: string; configuration: Record<string, unknown> }) => adminClient.put<AdminTenant>(`/tenants/${id}`, body), changeTenantStatus: (id: string, status: string) => adminClient.patch<AdminTenant>(`/tenants/${id}/status`, { status }),
  members: (tenantId: string) => adminClient.get<Membership[]>(`/tenants/${tenantId}/members`), addMember: (tenantId: string, body: { userId: string; role: string }) => adminClient.post<Membership>(`/tenants/${tenantId}/members`, body), changeMemberRole: (tenantId: string, userId: string, role: string) => adminClient.put<Membership>(`/tenants/${tenantId}/members/${userId}`, { role }), changeMemberStatus: (tenantId: string, userId: string, status: string) => adminClient.patch<Membership>(`/tenants/${tenantId}/members/${userId}/status`, { status }),
  audit: () => adminClient.get<AuditRecord[]>('/audit')
};
export const integrationApi = { list: () => integrationsClient.get<Array<{ id: string; provider: string; type: string; status: string; endpoint: string }>>('/'), executions: (id: string) => integrationsClient.get(`/${id}/executions`) };
export const analyticsApi = { overview: () => analyticsClient.get<Record<string, unknown>>('/overview') };
