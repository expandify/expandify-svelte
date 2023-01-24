<script lang="ts">
	import PlaylistView from '$lib/components/views/PlaylistView.svelte';
	import { reloadUserPlaylistsWithTracks } from '$lib/spotify/playlist';
	import { playlistCache } from '$lib/stores/cache';
</script>

<p>Playlists</p>

<button on:click={reloadUserPlaylistsWithTracks}>Refresh Playlists</button>


<PlaylistView playlists={$playlistCache.items}></PlaylistView>


<style lang="scss">

</style>
