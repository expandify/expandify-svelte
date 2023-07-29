import { writable, type Writable } from 'svelte/store';


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


