/// <reference types="@sveltejs/kit" />

// See https://kit.svelte.dev/docs/types#app
// for information about these interfaces

declare namespace App {
	interface Locals {
		loggedIn: boolean
		exportifyUser: import('src/lib/types').ExportifyUser
		BASE_URL: string
		jwt: string
		cookies: Record<string, string>
	}

	// interface Platform {}

	interface Session {
		loggedIn: boolean
		BASE_URL: string
	}

	interface Params {
		library: string
	}

	// interface Stuff {}
}
