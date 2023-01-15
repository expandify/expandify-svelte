import { Album } from '$lib/classes/Album';
import { Track } from '$lib/classes/Track';
import { Cache } from '$lib/stores/cache';
import type SpotifyWebApi from 'spotify-web-api-js';
import { handleRequest, makePagingRequest } from './request';
import { error } from '@sveltejs/kit';

export async function reloadSavedAlbumsWithTracks() {
	try {
		Cache.clear(Cache.albums);
		Cache.setState(Cache.albums, Cache.State.Loading);
		let currentLoaded = 0;

		const func = (api: SpotifyWebApi.SpotifyWebApiJs, offset: number) =>
			api.getMySavedAlbums({ limit: 50, offset });
		const albums = await makePagingRequest(func, (page) => {
			currentLoaded = currentLoaded + page.items.length;
			Cache.setTotalItems(Cache.albums, page.total);
			Cache.setLoadedItems(Cache.albums, currentLoaded);
		});

		Cache.setState(Cache.albums, Cache.State.LoadingSecondary);
		currentLoaded = 0;
		Cache.setTotalItems(Cache.albums, albums.length);
		Cache.setLoadedItems(Cache.albums, currentLoaded);

		for (const album of albums) {
			const tracksFunc = (api: SpotifyWebApi.SpotifyWebApiJs, offset: number) =>
				api.getAlbumTracks(album.album.id, { limit: 50, offset });
			const albumTracks = await makePagingRequest(tracksFunc, (_) => {}, album.album.tracks);
			const tracks = albumTracks.map((t) => Track.fromSimplifiedTrack(t));

			const toCache = Album.fromSavedAlbum(album, tracks);
			currentLoaded++;
			Cache.setLoadedItems(Cache.albums, currentLoaded);
			Cache.addItem(Cache.albums, toCache);
		}

		Cache.setState(Cache.albums, Cache.State.Finished);
	} catch (err) {		
		Cache.setState(Cache.albums, Cache.State.Error);
		console.error(err);
		throw error(500, "Error on loading Albums")
	}
}


export async function getAlbumNoTracks(id: string): Promise<Album> {

	const album = await handleRequest((api) => api.getAlbum(id));

	return Album.fromFullAlbum(album, []);
}
