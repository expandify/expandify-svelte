// See https://kit.svelte.dev/docs/types#app
// for information about these interfaces
// and what to do when importing types
declare namespace App {
	// interface Error {}
	// interface Locals {}
	// interface PageData {}
	// interface Platform {}
}

declare interface TokenApiResult {
	access_token: string;
	scope: string;
	expires_in: number;
	refresh_token: string;
}

declare interface AuthData {
	codeVerifier?: string;
	state?: string;
	token?: string;
	refresh_token?: string;
	tokenExpires?: string;
	tokenScope?: string;
}


declare interface State {
	authenticated: boolean;
}
