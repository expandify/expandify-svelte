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

if (browser) {
	try {
		const data: SpotifySession = JSON.parse(localStorage.spotify);
    spotifySession.set({...data, expirationDate: new Date(data.expirationDate)});
	} catch (error) {
		spotifySession.set(null);
	}
	spotifySession.subscribe((value) => (localStorage.spotify = JSON.stringify(value)));
}
