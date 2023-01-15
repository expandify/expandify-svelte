<script lang="ts">
	import type { Album } from '$lib/classes/Album';
	import type { Artist } from '$lib/classes/Artist';
	import type { Playlist } from '$lib/classes/Playlist';
	import type { Track } from '$lib/classes/Track';
	import { albumCache, artistCache, playlistCache, trackCache } from '$lib/stores/cache';

	let search: string = '';
	let delayedSearch = search;
	$: setTimeout(() => (delayedSearch = search), 500);

	let playlists: Playlist[];
	let playlistsTracks: { [id: string]: Track[] } = {};
	$: $playlistCache, playlists = searchPlaylists(delayedSearch);

	let tracks: Track[];
	$: $trackCache, tracks = searchTracks(delayedSearch, $trackCache.items);

	let albums: Album[];
	let albumTracks: { [id: string]: Track[] } = {};
	$: $albumCache, albums = searchAlbums(delayedSearch);

	let artists: Artist[];
	$: $artistCache, artists = searchArtists(delayedSearch);

	function searchArtists(filter: string) {
		filter = filter.toLowerCase();
		const filteredArtists = [];

		for (const artist of $artistCache.items) {
			const name = artist.name.toLowerCase();

			if (name.indexOf(filter) !== -1) {
				filteredArtists.push(artist);
			}
		}
		return filteredArtists;
	}

	function searchTracks(filter: string, tracks: Track[]) {
		filter = filter.toLowerCase();
		const filteredTracks = [];

		for (const track of tracks) {
			const name = track.name.toLowerCase();
			const artists = track.artists
				.map((x) => x.name)
				.join(', ')
				.toLowerCase();
			const album = track.album?.name.toLowerCase();

			if (
				name.indexOf(filter) !== -1 ||
				artists.indexOf(filter) !== -1 ||
				(album && album.indexOf(filter) !== -1)
			) {
				filteredTracks.push(track);
			}
		}
		return filteredTracks;
	}

	function searchPlaylists(filter: string) {
		filter = filter.toLowerCase();
		const filteredPlaylists = [];

		for (const playlist of $playlistCache.items) {
			const name = playlist.name.toLowerCase();

			const tracks = searchTracks(filter, playlist.tracks);
			playlistsTracks[playlist.id] = tracks;

			if (name.indexOf(filter) !== -1 || tracks.length > 0) {
				filteredPlaylists.push(playlist);
			}
		}
		return filteredPlaylists;
	}

	function searchAlbums(filter: string) {
		filter = filter.toLowerCase();
		const filterdAlbums = [];

		for (const album of $albumCache.items) {
			const name = album.name.toLowerCase();

			const tracks = searchTracks(filter, album.tracks);
			albumTracks[album.id] = tracks;

			if (name.indexOf(filter) !== -1 || tracks.length > 0) {
				filterdAlbums.push(album);
			}
		}
		return filterdAlbums;
	}
</script>

<p>Search for a song in your library.</p>

<input bind:value={search} placeholder="Find a song..." />

<div class="playlists">
	{#if playlists.length > 0}
		<h1>Playlists</h1>
	{/if}
	{#each playlists as playlist}
		<h3>{playlist.name}</h3>
		{#each playlistsTracks[playlist.id] as track}
			<span>{track.name}</span>
		{/each}
	{/each}

	{#if tracks.length > 0}
		<h1>Saved Tracks</h1>
	{/if}
	{#each tracks as track}
		<span>{track.name}</span>
	{/each}

	{#if albums.length > 0}
		<h1>Saved Albums</h1>
	{/if}
	{#each albums as album}
		<h3>{album.name}</h3>
		{#each albumTracks[album.id] as track}
			<span>{track.name}</span>
		{/each}
	{/each}

	{#if artists.length > 0}
		<h1>Saved Tracks</h1>
	{/if}
	{#each artists as artist}
		<span>{artist.name}</span>
	{/each}
</div>

<style lang="scss">
	.playlists {
		display: flex;
		flex-direction: column;
	}
</style>
