import { authenticate } from '$lib/auth/authorize';
import { authData } from '$lib/stores/authData';
import { error } from '@sveltejs/kit';
import SpotifyWebApi from 'spotify-web-api-js';
import { get } from 'svelte/store';

/**
 * Makes a paginated request to spotify.
 * Resolves the pagination and returns the full list of items.
 *
 * @param func The function to be called.
 * @returns The list of items.
 */
export async function makePagingRequest<T>(
	func: (api: SpotifyWebApi.SpotifyWebApiJs, offset: number) => Promise<SpotifyApi.PagingObject<T>>,
	callback: (page: SpotifyApi.PagingObject<T>) => void = (_) => {},
	initialPage: SpotifyApi.PagingObject<T> | null = null
) {
	let offset = initialPage?.offset || 0;
	// The placeholder is used for the first loop when no initial page is given. A non null value is needed to enter the loop.
	let next: string | null = initialPage?.next || 'placeholder';
	let payload: T[] = initialPage?.items || [];
	while (next) {
		const data = await handleRequest((api) => func(api, offset));
		offset += data.limit;
		next = data.next;
		payload = [...payload, ...data.items];

		callback(data);
	}

	return payload;
}

/**
 * Makes a paginated request to spotify.
 * Resolves the pagination and returns the full list of items.
 *
 * @param func The function to be called.
 * @returns The list of items.
 */
export async function makeCursorRequest<T>(
	func: (
		api: SpotifyWebApi.SpotifyWebApiJs,
		after: string | null
	) => Promise<SpotifyApi.CursorBasedPagingObject<T>>,
	callback: (cursor: SpotifyApi.CursorBasedPagingObject<T>) => void = (_) => {},
	initialCursor: SpotifyApi.CursorBasedPagingObject<T> | null = null
) {
	let after: string | null = initialCursor?.cursors.after || null;

	// The placeholder is used for the first loop when no initial page is given. A non null value is needed to enter the loop.
	let next: string | null = initialCursor?.next || 'placeholder';
	let payload: T[] = initialCursor?.items || [];
	while (next) {
		const data = await handleRequest((api) => func(api, after));
		after = data.cursors.after;
		next = data.next;
		payload = [...payload, ...data.items];

		callback(data);
	}

	return payload;
}

/**
 * Takes an async function and calls it.
 * Tries to solve any occuring errors, during the request.
 *
 * @param func The function to be called.
 * @returns The function's return value
 */
export async function handleRequest<T>(func: (api: SpotifyWebApi.SpotifyWebApiJs) => Promise<T>) {
	let api = await getAuthenticatedApi();

	let httpRequest: XMLHttpRequest;
	try {
		// Make the initial request.
		return await func(api);
	} catch (err: any) {
		httpRequest = err;
	}

	try {
		// Try to handle the errors and retry the request.
		await handleRequestErrors(httpRequest);
		api = await getAuthenticatedApi();
		return await func(api);
	} catch (err: any) {
		// Throw an error, when the second request also fails.
		throw error(500, { message: 'Error making the request.' });
	}
}

/**
 * Creats an SpotifyWebApi with the current access token from the auth data.
 * @returns The SpotifyWebApi
 */
async function getAuthenticatedApi() {
	await authenticate();
	const spotifyApi = new SpotifyWebApi();
	spotifyApi.setAccessToken(get(authData).token!);
	return spotifyApi;
}

/**
 * Handles known errors.
 *
 * Refreshes the access token, when it is expired.
 * Waits for the specified time, when the request is rate limited.
 *
 * @param err The Request
 * @returns Nothing
 */
async function handleRequestErrors(err: XMLHttpRequest) {
	if (err.status === 401) {
		// Authentication will renew the access_token, when possible
		await authenticate();
		return;
	}

	if (err.status === 429) {
		const retry = err.getResponseHeader('Retry-After');
		await new Promise((r) => setTimeout(r, Number(retry) * 1000));
		return;
	}
}
