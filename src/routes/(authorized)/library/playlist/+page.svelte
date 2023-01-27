<script lang="ts">
	import { reloadUserPlaylistsWithTracks } from '$lib/spotify/playlist';
	import { playlistCache } from '$lib/stores/cache';
	import ButtonSimpleElevated from '$lib/components/buttons/ButtonSimpleElevated.svelte';
	import CardGrid from '$lib/components/layout/CardGrid.svelte';

	$: playlists = $playlistCache.items.map(playlist => ({
		title: playlist.name,
		subtitle: `By ${playlist.owner.display_name}`,
		image: playlist.images.at(0)?.url,
		fallbackSvg: "playlist",
		href: null
	}))

</script>

<div class="header">
	<h2>Playlists</h2>
	<ButtonSimpleElevated on:click={reloadUserPlaylistsWithTracks}>Reload Playlists</ButtonSimpleElevated>
</div>

<CardGrid cards={playlists} />

<style lang="scss">

	.header {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
</style>
