import { Track } from '$lib/classes/Track';
import { Cache } from '$lib/stores/cache';
import type SpotifyWebApi from 'spotify-web-api-js';
import { makePagingRequest } from './request';
import { error } from '@sveltejs/kit';

export async function reloadSavedTracks() {
	try {
		Cache.clear(Cache.tracks);
		Cache.setState(Cache.tracks, Cache.State.Loading);
		let currentLoaded = 0;

		const func = (api: SpotifyWebApi.SpotifyWebApiJs, offset: number) =>
			api.getMySavedTracks({ limit: 50, offset });
		await makePagingRequest(func, (page) => {
			currentLoaded = currentLoaded + page.items.length;
			Cache.setTotalItems(Cache.tracks, page.total);
			Cache.setLoadedItems(Cache.tracks, currentLoaded);

			const toCache = page.items.map((t) => Track.fromSavedTrack(t));
			Cache.addItems(Cache.tracks, toCache);
		});

		Cache.setState(Cache.tracks, Cache.State.Finished);
	} catch (err) {
		Cache.setState(Cache.tracks, Cache.State.Error);
		console.error(err);
		throw error(500, "Error on loading Tracks")
	}
}
