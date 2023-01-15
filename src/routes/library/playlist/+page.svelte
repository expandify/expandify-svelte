<script lang="ts">
	import PlaylistView from '$lib/components/PlaylistView.svelte';
	import { reloadUserPlaylistsWithTracks } from '$lib/spotify/playlist';
	import { playlistCache } from '$lib/stores/cache';
</script>

<p>Playlists</p>

<button on:click={reloadUserPlaylistsWithTracks}>Refresh Playlists</button>


<PlaylistView playlists={$playlistCache.items}></PlaylistView>


<style lang="scss">
	.playlists {
		display: flex;
		flex-direction: column;
	}
</style>
