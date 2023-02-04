<script lang="ts">
	import ButtonSimpleElevated from '$lib/components/buttons/ButtonSimpleElevated.svelte';
	import PlaylistCard from '$lib/components/cards/PlaylistCard.svelte';
	import Grid from '$lib/components/layout/Grid.svelte';
	import { loadUserPlaylistsWithTracks } from '$lib/spotify/api/playlsits';
	import { playlistStore } from '$lib/stores/library/playlists';

</script>

<div class="header">
	<h2>Playlists - {$playlistStore.playlists.length}</h2>
	<ButtonSimpleElevated on:click={loadUserPlaylistsWithTracks}>Reload Playlists</ButtonSimpleElevated>
</div>

<Grid>
	{#each $playlistStore.playlists as playlist}
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
