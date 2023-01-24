import { get, writable } from 'svelte/store';
import { browser } from '$app/environment';
import { Base64 } from 'js-base64';


declare interface Spotify {
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
export const spotifyData = writable<Spotify>({});

if (browser) {
	let data = {};
	try {
		data = JSON.parse(localStorage.spotify);		
	} catch (error) {
		localStorage.clear();
		data = {};
	}

	spotifyData.set(data);
	spotifyData.subscribe((value) => (localStorage.spotify = JSON.stringify(value)));

}

function updateLoggedIn(): boolean {
	const store = get(spotifyData);
	if (!store.codeVerifier) return false;
	if (!store.state) return false;
	if (!store.token) return false;
	if (!store.refresh_token) return false;
	if (!store.tokenExpires) return false;
	if (!store.tokenScope) return false;

	if (store.codeVerifier.length != Spotify.CODE_VERIFIER_LENGTH) return false;
	if (Base64.decode(store.state).length != Spotify.STATE_LENGTH) return false;
	if (store.tokenScope != Spotify.SCOPES) return false;

	return true;
}

export namespace Spotify {
	export const SCOPES = 'playlist-read-private playlist-read-collaborative user-library-read user-follow-read user-read-private';
	export const CODE_VERIFIER_LENGTH = 50;
	export const STATE_LENGTH = 50;
	export const TOKEN_API_URL = 'https://accounts.spotify.com/api/token';
	export const AUTHORIZE_URL = 'https://accounts.spotify.com/authorize';	

	export const data = spotifyData;
	export let isLoggedIn: Boolean = false;
	
	
	spotifyData.subscribe((_) => isLoggedIn = updateLoggedIn());

	export function logout() {
		spotifyData.set({});
	}
}

