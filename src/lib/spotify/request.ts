import { error } from '@sveltejs/kit';
import SpotifyWebApi from 'spotify-web-api-js';
import { get } from 'svelte/store';

import { PUBLIC_SPOTIFY_ID } from '$env/static/public';
import { Spotify, spotifyData } from '$lib/stores/spotify';
const TOKEN_API_URL = 'https://accounts.spotify.com/api/token';	



export async function paginagedRequest<T>(
	func: (api: SpotifyWebApi.SpotifyWebApiJs, offset: number) => Promise<SpotifyApi.PagingObject<T>>,
	callback: (page: SpotifyApi.PagingObject<T>) => void = (_) => {},
	offset = 0,
	next = 'NonNullPlaceholder',
	payload: T[] = [],
): Promise<T[]> {
	return handleRequest((api) => func(api, offset))
		.then((data) => {callback(data); return data})
		.then((data) => { 
			if (!next) { return [...payload, ...data.items] };	
			return paginagedRequest(func, callback, offset + data.limit, data.next, [...payload, ...data.items]);
		 })
		.catch(() => Promise.reject({status: 500, message: "Error fetching paginated data from spotify."}))
}

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
	let api = new SpotifyWebApi();
	api.setAccessToken(get(Spotify.data).token!);

	return func(api)
		.catch(async (err) => {
			
			switch (err.status) {
				case 401:
					await refreshToken();
					api.setAccessToken(get(Spotify.data).token!);
					
					break;
				case 429:
					const retry = err.getResponseHeader('Retry-After');
					await new Promise((r) => setTimeout(r, Number(retry) * 1000))
					break;
				default:
					return Promise.reject({status: 500, message: `Error fetching data from spotify. Spotify code: ${err.status}`})
			}

			return func(api);
		})
		.catch((err) => Promise.reject({status: 500, message: `Error fetching data from spotify. Spotify code: ${err.status}`}))
}

async function refreshToken() {
	
	const response = await fetch(TOKEN_API_URL, {
		method: 'POST',
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
		body: new URLSearchParams({
			grant_type: 'refresh_token',
			client_id: PUBLIC_SPOTIFY_ID,
			refresh_token: get(Spotify.data).refresh_token!
		}).toString()
	});

	if (!response.ok) {
		return Promise.reject({status: 401, message: "Cloud not refresh access token"});
	}

	const json: TokenApiResult = await response.json();
	
	Spotify.data.update((value) => ({
		...value,
		token: json.access_token,
		tokenScope: json.scope,
		tokenExpires: new Date(Date.now() + Number(json.expires_in) * 1000).toISOString()
	}));
	
	return Promise.resolve();
}