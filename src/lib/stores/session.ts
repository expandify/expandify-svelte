import { writable, type Writable } from 'svelte/store';
import { browser } from '$app/environment';


export type Session = {
	token: string,
	refreshToken: string,
	expirationDate: Date,
	tokenScope: string,
}

/**
 * Simple store, all data for spotify authentication.
 */
export const session: Writable<Session | null> = writable(null);

if (browser) {
	try {
		const data: Session = JSON.parse(localStorage.spotify);
    session.set({...data, expirationDate: new Date(data.expirationDate)});
	} catch (error) {
		session.set(null);
	}
	session.subscribe((value) => (localStorage.spotify = JSON.stringify(value)));
}
