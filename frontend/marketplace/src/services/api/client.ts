import axios from 'axios';

export const apiClient = axios.create({ baseURL: import.meta.env.VITE_API_URL ?? '/api', timeout: 10000 });
apiClient.interceptors.request.use((config) => { config.headers['X-Correlation-ID'] ??= crypto.randomUUID(); return config; });
export const productApi = { list: () => apiClient.get('/products'), get: (id: string) => apiClient.get(`/products/${id}`), publish: (id: string) => apiClient.post(`/products/${id}/publish`) };
