import { writable } from 'svelte/store';
import { browser } from '$app/environment';

export const authData = writable<AuthData>({});

if (browser) {
	let data = {};
	try {
		data = JSON.parse(localStorage.authData);
	} catch (error) {
		localStorage.clear();
		data = {};
	}

	authData.set(data);
	authData.subscribe((value) => (localStorage.authData = JSON.stringify(value)));
}

/**
 * Clear the Store.
 *
 */
export function clearAuthData() {
	authData.set({});
}

/**
 * Sets the codeVerifier field on the users authData.
 *
 * @param {string} codeVerifier
 */
export function setCodeVerifier(codeVerifier: string) {
	authData.update((current) => ({ ...current, codeVerifier: codeVerifier }));
}

/**
 * Sets the state field on the users authData.
 *
 * @param {string} state
 */
export function setState(state: string) {
	authData.update((current) => ({ ...current, state: state }));
}

/**
 * Sets the token field on the users authData.
 *
 * @param {string} token
 */
export function setToken(token: string) {
	authData.update((current) => ({ ...current, token: token }));
}

/**
 * Sets the refresh_token field on the users authData.
 *
 * @param {string} refresh_token
 */
export function setRefreshToken(refresh_token: string) {
	authData.update((current) => ({ ...current, refresh_token: refresh_token }));
}

/**
 * Sets the tokenExpires field on the users authData.
 *
 * @param {string} tokenExpires
 */
export function setTokenExpires(tokenExpires: string) {
	authData.update((current) => ({ ...current, tokenExpires: tokenExpires }));
}

/**
 * Sets the tokenScope field on the users authData.
 *
 * @param {string} tokenScope
 */
export function setTokenScope(tokenScope: string) {
	authData.update((current) => ({ ...current, tokenScope: tokenScope }));
}
