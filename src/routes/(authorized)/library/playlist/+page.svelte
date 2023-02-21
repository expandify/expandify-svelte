<script lang="ts">
	import ButtonSimpleElevated from '$lib/components/buttons/ButtonSimpleElevated.svelte';
	import PlaylistCard from '$lib/components/cards/PlaylistCard.svelte';
	import Grid from '$lib/components/layout/Grid.svelte';
	import { Spotify } from '$lib/data/spotify';
	import { dependencies } from '$lib/stores/dependencies';
	import { playlists } from '$lib/stores/library/playlists';

	dependencies.onlyPlaylistsNeeded();
</script>

<div class="header">
	<h2>Playlists - {$playlists.playlists.length}</h2>
	<ButtonSimpleElevated on:click={Spotify.Playlist.loadAllToStore}>Reload Playlists</ButtonSimpleElevated>
</div>

<Grid>
	{#each $playlists.playlists as playlist}
		<PlaylistCard playlist={playlist}/>
	{/each}
</Grid>

<style lang="scss">

	.header {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
</style>
