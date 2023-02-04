<script lang="ts">
	import { track } from "$lib/spotify/converter";
	import { albumStore } from "$lib/stores/library/albums";
	import { artistStore } from "$lib/stores/library/artists";
	import { playlistStore } from "$lib/stores/library/playlists";
	import { trackStore } from "$lib/stores/library/tracks";


	
	let search: string = '';
	let delayedSearch = search;
	$: setTimeout(() => (delayedSearch = search), 500);
	
	let playlists: Playlist[];
	let playlistsTracks: { [id: string]: Track[] } = {};
	$: $playlistStore, playlists = searchPlaylists(delayedSearch);

	let tracks: Track[];
	$: $trackStore, tracks = searchTracks(delayedSearch, $trackStore.tracks);

	let albums: Album[];
	let albumTracks: { [id: string]: Track[] } = {};
	$: $albumStore, albums = searchAlbums(delayedSearch);

	
	let artists: Artist[];
	$: $artistStore, artists = searchArtists(delayedSearch);

	function searchArtists(filter: string) {
		filter = filter.toLowerCase();
		const filteredArtists = [];

		for (const artist of $artistStore.artists) {
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

			// Will be null for TrackSimplified. Is ok, since it will be ignored
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

		for (const playlist of $playlistStore.playlists) {
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

		for (const album of $albumStore.albums) {
			const name = album.name.toLowerCase();
			
			const tracks = searchTracks(filter, album.tracks as Track[]);
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
