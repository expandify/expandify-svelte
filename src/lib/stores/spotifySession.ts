import { writable, type Writable } from 'svelte/store';
import { browser } from '$app/environment';


export type SpotifySession = {
	token: string,
	refreshToken: string,
	expirationDate: Date,
	tokenScope: string,
}

/**
 * Simple store, all data for spotify authentication.
 */
export const spotifySession: Writable<SpotifySession | null> = writable(null);


