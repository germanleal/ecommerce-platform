import { useAuthStore, type SessionUser } from '../../store/authStore';

const issuer = import.meta.env.VITE_OIDC_ISSUER;
const clientId = import.meta.env.VITE_OIDC_CLIENT_ID ?? 'web-client';
const redirectUri = `${window.location.origin}/auth/callback`;
const decode = (token: string): Record<string, unknown> => JSON.parse(atob(token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/')));
const random = () => crypto.getRandomValues(new Uint8Array(32)).reduce((text, value) => text + value.toString(16).padStart(2, '0'), '');
const challenge = async (value: string) => btoa(String.fromCharCode(...new Uint8Array(await crypto.subtle.digest('SHA-256', new TextEncoder().encode(value))))) .replace(/\+/g, '-').replace(/\//g, '_').replace(/=+$/g, '');

export async function login() {
  if (!issuer) throw new Error('OIDC is not configured.');
  const state = random(), verifier = random();
  sessionStorage.setItem('oidc_state', state); sessionStorage.setItem('oidc_verifier', verifier);
  const url = new URL(`${issuer}/protocol/openid-connect/auth`);
  url.search = new URLSearchParams({ client_id: clientId, response_type: 'code', scope: 'openid profile', redirect_uri: redirectUri, state, code_challenge: await challenge(verifier), code_challenge_method: 'S256' }).toString();
  window.location.assign(url.toString());
}
export async function completeLogin() {
  if (!issuer) throw new Error('OIDC is not configured.');
  const params = new URLSearchParams(window.location.search);
  if (params.get('state') !== sessionStorage.getItem('oidc_state') || !params.get('code')) throw new Error('Invalid login response.');
  const body = new URLSearchParams({ grant_type: 'authorization_code', client_id: clientId, code: params.get('code')!, redirect_uri: redirectUri, code_verifier: sessionStorage.getItem('oidc_verifier') ?? '' });
  const response = await fetch(`${issuer}/protocol/openid-connect/token`, { method: 'POST', headers: { 'Content-Type': 'application/x-www-form-urlencoded' }, body });
  if (!response.ok) throw new Error('Could not establish session.');
  const tokens = await response.json() as { access_token: string; refresh_token?: string };
  if (tokens.refresh_token) sessionStorage.setItem('refresh_token', tokens.refresh_token);
  hydrate(tokens.access_token); sessionStorage.removeItem('oidc_state'); sessionStorage.removeItem('oidc_verifier');
}
export function hydrate(token = sessionStorage.getItem('access_token')) {
  if (!token) return false;
  try {
    const claims = decode(token), realm = claims.realm_access as { roles?: string[] } | undefined;
    if (typeof claims.exp === 'number' && claims.exp * 1000 <= Date.now()) throw new Error('expired');
    const roles = [...new Set([...(Array.isArray(claims.roles) ? claims.roles as string[] : []), ...(realm?.roles ?? [])])];
    const user: SessionUser = { id: String(claims.sub), name: String(claims.preferred_username ?? claims.name ?? claims.sub), tenantId: typeof claims.tenant_id === 'string' ? claims.tenant_id : typeof claims.tenantId === 'string' ? claims.tenantId : undefined };
    useAuthStore.getState().setSession(user, token, roles, Array.isArray(claims.permissions) ? claims.permissions as string[] : []); return true;
  } catch { useAuthStore.getState().logout(); return false; }
}
export function logout() { const hint = sessionStorage.getItem('access_token'); useAuthStore.getState().logout(); if (issuer && hint) { const url = new URL(`${issuer}/protocol/openid-connect/logout`); url.searchParams.set('post_logout_redirect_uri', window.location.origin); url.searchParams.set('id_token_hint', hint); window.location.assign(url.toString()); } else window.location.assign('/'); }
