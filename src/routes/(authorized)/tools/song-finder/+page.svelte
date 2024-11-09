<script lang="ts">
	import { albums } from '$lib/stores/library/albums';
	import { artists } from '$lib/stores/library/artists';
	import { playlists } from '$lib/stores/library/playlists';
	import { tracks } from '$lib/stores/library/tracks';
	import type { Album, Artist, Playlist, Track } from '$lib/types/spotify';
	import { dependencies } from '$lib/stores/dependencies';

	dependencies.setDependencies(true, true, true, true, true);

	let delayTimer = $state(-1);

	let search = $state('');
	let filteredPlaylists = $derived(searchPlaylists(search, $playlists.playlists));
	let filteredTracks = $derived(searchTracks(search, $tracks.tracks));
	let filteredAlbums = $derived(searchAlbums(search, $albums.albums));
	let filteredArtists = $derived(searchArtists(search, $artists.artists));

	$effect(()=> console.log(search))

	function updateSearch(e: Event) {
		clearTimeout(delayTimer);
		delayTimer = +setTimeout(() => (search = (e.target as HTMLInputElement).value || ''), 500);
	}

	function searchArtists(filter: string, artists: Artist[]) {
		filter = filter.toLowerCase();
		const filteredArtists = [];

		for (const artist of artists) {
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

	function searchPlaylists(filter: string, playlists: Playlist[]) {
		filter = filter.toLowerCase();
		const filteredPlaylists: {playlist: Playlist, tracks: Track[]}[] = [];

		for (const playlist of playlists) {
			const name = playlist.name.toLowerCase();

			const tracks = searchTracks(filter, playlist.tracks);

			if (name.indexOf(filter) !== -1 || tracks.length > 0) {
				filteredPlaylists.push({ playlist, tracks });
			}
		}
		return filteredPlaylists;
	}

	function searchAlbums(filter: string, albums: Album[]) {
		filter = filter.toLowerCase();
		const filteredAlbums: {album: Album, tracks: Track[]}[] = [];

		for (const album of albums) {
			const name = album.name.toLowerCase();

			const tracks = searchTracks(filter, album.tracks as Track[]);

			if (name.indexOf(filter) !== -1 || tracks.length > 0) {
				filteredAlbums.push({ album, tracks });
			}
		}
		return filteredAlbums;
	}
</script>

<p>Search for a song in your library.</p>

<input oninput="{updateSearch}" placeholder="Find a song..." />

<div class="flex flex-col">
	{#if filteredPlaylists.length > 0}
		<h1>Playlists</h1>
	{/if}
	{#each filteredPlaylists as filteredPlaylist}
		<h3>{filteredPlaylist.playlist.name}</h3>
		<ul class="space-y-1 text-gray-500 dark:text-gray-400">
			{#each filteredPlaylist.tracks as track}
				<li>{track.name} -- {track.album.name} -- {track.artists.map(a => a.name).join(", ")}</li>
			{/each}
		</ul>
	{/each}

	{#if filteredTracks.length > 0}
		<h1>Saved Songs</h1>
	{/if}
	<ul class="space-y-1 text-gray-500 dark:text-gray-400">
	{#each filteredTracks as track}
		<li>{track.name} -- {track.album.name} -- {track.artists.map(a => a.name).join(", ")}</li>
	{/each}
	</ul>

	{#if filteredAlbums.length > 0}
		<h1>Saved Albums</h1>
	{/if}
	{#each filteredAlbums as filteredAlbum}
		<h3>{filteredAlbum.album.name}</h3>
		<ul class="space-y-1 text-gray-500 dark:text-gray-400">
		{#each filteredAlbum.tracks as track}
			<li>{track.name} -- {track.album.name} -- {track.artists.map(a => a.name).join(", ")}</li>
		{/each}
		</ul>
	{/each}

	{#if filteredArtists.length > 0}
		<h1>Followed Artists</h1>
	{/if}
	<ul class="space-y-1 text-gray-500 dark:text-gray-400">
	{#each filteredArtists as artist}
		<li>{artist.name}</li>
	{/each}
	</ul>
</div>
