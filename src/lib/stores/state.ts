import { writable } from 'svelte/store';
import { browser } from '$app/environment';
import { authData } from './authData';
import { isAuthenticated } from '$lib/auth/authorize';

let darkTheme = false;

if (browser && window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
	darkTheme = true;
}

/**
 * Simple store, that holds the state of the user.
 * When the authDate changes this store will reevaluate.
 */
export const state = writable<State>({ authenticated: isAuthenticated(), darkTheme: darkTheme });



if (browser) {
	authData.subscribe((_) =>	
		state.update((value) => ({ ...value, authenticated: isAuthenticated() }))
	);

	var observer = new MutationObserver(function(mutations) {
		const preferedDark = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
		const dataTheme = document.documentElement.getAttribute("data-theme");

		if (!dataTheme) {
			state.update((value) => ({ ...value, darkTheme:  preferedDark}));
			return;
		}

		state.update((value) => ({ ...value, darkTheme:  dataTheme === "dark"}));

		
	});
	
	observer.observe(document.documentElement, {
		attributes: true 
	});	
}
