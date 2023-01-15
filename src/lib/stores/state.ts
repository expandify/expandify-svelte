import { writable } from 'svelte/store';
import { browser } from '$app/environment';
import { authData } from './authData';
import { isAuthenticated } from '$lib/auth/authorize';

/**
 * Simple store, that holds the state of the user.
 * When the authDate changes this store will reevaluate.
 */
export const state = writable<State>({ authenticated: isAuthenticated() });



if (browser) {
	authData.subscribe((_) =>	
		state.update((value) => ({ ...value, authenticated: isAuthenticated() }))
	);	
}
