import { get, writable } from 'svelte/store';
import { browser } from '$app/environment';
import { Base64 } from 'js-base64';
import { CODE_VERIFIER_LENGTH, SCOPES, STATE_LENGTH } from '$lib/spotify/constants';


declare interface SpotifyAccess {
	codeVerifier?: string;
	state?: string;
	token?: string;
	refresh_token?: string;
	tokenExpires?: string;
	tokenScope?: string;
}

/**
 * Simple store, all data for spotify authentication.
 */
export const spotifyAccessData = writable<SpotifyAccess>({});
export let hasSpotifyAccess = false;

if (browser) {
	let data: SpotifyAccess = {};
	try {
		data = JSON.parse(localStorage.spotify);		
	} catch (error) {
		localStorage.clear();
		data = {};
	}
	hasSpotifyAccess = validateSpotifyAccess();
	spotifyAccessData.set(data);	
	spotifyAccessData.subscribe((value) => (localStorage.spotify = JSON.stringify(value)));	
	spotifyAccessData.subscribe((_) => hasSpotifyAccess = validateSpotifyAccess());
}

export function removeAccess() {
	spotifyAccessData.set({});
}

function validateSpotifyAccess(): boolean {
	const store = get(spotifyAccessData);
	if (!store.codeVerifier) return false;
	if (!store.state) return false;
	if (!store.token) return false;
	if (!store.refresh_token) return false;
	if (!store.tokenExpires) return false;
	if (!store.tokenScope) return false;

	if (store.codeVerifier.length != CODE_VERIFIER_LENGTH) return false;
	if (Base64.decode(store.state).length != STATE_LENGTH) return false;
	if (store.tokenScope != SCOPES) return false;

	return true;
}

