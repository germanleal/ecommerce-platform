import { useAuthStore, type SessionUser } from '../../store/authStore';

const issuer = import.meta.env.VITE_OIDC_ISSUER;
const clientId = import.meta.env.VITE_OIDC_CLIENT_ID ?? 'web-client';
const redirectUri = `${window.location.origin}/auth/callback`;
const silentCheckKey = 'oidc_sso_checked';
const loginModeKey = 'oidc_login_mode';
const silentCheckRecentlyRan = () => Date.now() - Number(sessionStorage.getItem(silentCheckKey) ?? 0) < 10000;
const decode = (token: string): Record<string, unknown> => JSON.parse(atob(token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/')));
const random = () => crypto.getRandomValues(new Uint8Array(32)).reduce((text, value) => text + value.toString(16).padStart(2, '0'), '');
const challenge = async (value: string) => btoa(String.fromCharCode(...new Uint8Array(await crypto.subtle.digest('SHA-256', new TextEncoder().encode(value))))) .replace(/\+/g, '-').replace(/\//g, '_').replace(/=+$/g, '');

async function authorize(mode: 'interactive' | 'silent') {
  if (!issuer) throw new Error('OIDC is not configured.');
  const state = random(), verifier = random();
  sessionStorage.setItem('oidc_state', state); sessionStorage.setItem('oidc_verifier', verifier);
  sessionStorage.setItem(loginModeKey, mode);
  sessionStorage.setItem('post_login_path', `${window.location.pathname}${window.location.search}`);
  const url = new URL(`${issuer}/protocol/openid-connect/auth`);
  const parameters: Record<string, string> = { client_id: clientId, response_type: 'code', scope: 'openid profile', redirect_uri: redirectUri, state, code_challenge: await challenge(verifier), code_challenge_method: 'S256' };
  if (mode === 'silent') parameters.prompt = 'none';
  url.search = new URLSearchParams(parameters).toString();
  window.location.assign(url.toString());
}
export async function login() { sessionStorage.removeItem(silentCheckKey); return authorize('interactive'); }
export async function completeLogin(): Promise<string> {
  if (!issuer) throw new Error('OIDC is not configured.');
  const params = new URLSearchParams(window.location.search);
  const silent = sessionStorage.getItem(loginModeKey) === 'silent';
  if (params.get('state') !== sessionStorage.getItem('oidc_state')) throw new Error('Invalid login response.');
  if (params.get('error')) {
    const returnTo = sessionStorage.getItem('post_login_path') ?? '/';
    sessionStorage.setItem(silentCheckKey, String(Date.now()));
    sessionStorage.removeItem('oidc_state'); sessionStorage.removeItem('oidc_verifier'); sessionStorage.removeItem(loginModeKey);
    sessionStorage.removeItem('post_login_path');
    if (silent && ['login_required', 'interaction_required'].includes(params.get('error') ?? '')) return returnTo.startsWith('/') && !returnTo.startsWith('//') ? returnTo : '/';
    throw new Error('Could not establish session.');
  }
  if (!params.get('code')) throw new Error('Invalid login response.');
  const body = new URLSearchParams({ grant_type: 'authorization_code', client_id: clientId, code: params.get('code')!, redirect_uri: redirectUri, code_verifier: sessionStorage.getItem('oidc_verifier') ?? '' });
  const response = await fetch(`${issuer}/protocol/openid-connect/token`, { method: 'POST', headers: { 'Content-Type': 'application/x-www-form-urlencoded' }, body });
  if (!response.ok) throw new Error('Could not establish session.');
  const tokens = await response.json() as { access_token: string; refresh_token?: string; id_token?: string };
  storeTokens(tokens);
  hydrate(tokens.access_token); sessionStorage.setItem(silentCheckKey, String(Date.now())); sessionStorage.removeItem('oidc_state'); sessionStorage.removeItem('oidc_verifier'); sessionStorage.removeItem(loginModeKey);
  const returnTo = sessionStorage.getItem('post_login_path') ?? '/';
  sessionStorage.removeItem('post_login_path');
  return returnTo.startsWith('/') && !returnTo.startsWith('//') ? returnTo : '/';
}
function storeTokens(tokens: { access_token: string; refresh_token?: string; id_token?: string }) { if (tokens.refresh_token) sessionStorage.setItem('refresh_token', tokens.refresh_token); if (tokens.id_token) sessionStorage.setItem('id_token', tokens.id_token); }
export function hydrate(token = sessionStorage.getItem('access_token')) {
  if (!token) return false;
  try {
    const claims = decode(token), realm = claims.realm_access as { roles?: string[] } | undefined;
    if (typeof claims.exp === 'number' && claims.exp * 1000 <= Date.now()) throw new Error('expired');
    const roles = [...new Set([...(Array.isArray(claims.roles) ? claims.roles as string[] : []), ...(realm?.roles ?? [])])];
    const user: SessionUser = { id: String(claims.sub), name: String(claims.preferred_username ?? claims.name ?? claims.sub), tenantId: typeof claims.tenant_id === 'string' ? claims.tenant_id : typeof claims.tenantId === 'string' ? claims.tenantId : undefined };
    useAuthStore.getState().setSession(user, token, roles, Array.isArray(claims.permissions) ? claims.permissions as string[] : []); return true;
  } catch { useAuthStore.getState().clearAccessToken(); return false; }
}
async function refreshSession() {
  const refreshToken = sessionStorage.getItem('refresh_token');
  if (!issuer || !refreshToken) return false;
  const body = new URLSearchParams({ grant_type: 'refresh_token', client_id: clientId, refresh_token: refreshToken });
  try {
    const response = await fetch(`${issuer}/protocol/openid-connect/token`, { method: 'POST', headers: { 'Content-Type': 'application/x-www-form-urlencoded' }, body });
    if (!response.ok) { useAuthStore.getState().logout(); return false; }
    const tokens = await response.json() as { access_token: string; refresh_token?: string; id_token?: string };
    storeTokens(tokens);
    return hydrate(tokens.access_token);
  } catch { useAuthStore.getState().logout(); return false; }
}
export async function restoreSession() {
  if (hydrate()) return true;
  if (await refreshSession()) return true;
  if (issuer && !silentCheckRecentlyRan() && window.location.pathname !== '/auth/callback') {
    sessionStorage.setItem(silentCheckKey, String(Date.now()));
    await authorize('silent');
  }
  return false;
}
export function logout() { const hint = sessionStorage.getItem('id_token') ?? sessionStorage.getItem('access_token'); useAuthStore.getState().logout(); sessionStorage.removeItem('id_token'); sessionStorage.setItem(silentCheckKey, String(Date.now())); if (issuer && hint) { const url = new URL(`${issuer}/protocol/openid-connect/logout`); url.searchParams.set('post_logout_redirect_uri', window.location.origin); url.searchParams.set('id_token_hint', hint); window.location.assign(url.toString()); } else window.location.assign('/'); }
