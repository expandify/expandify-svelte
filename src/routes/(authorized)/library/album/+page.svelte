<script lang="ts">
	import { reloadSavedAlbumsWithTracks } from '$lib/spotify/album';
	import { albumCache } from '$lib/stores/cache';
	import ButtonSimpleElevated from '$lib/components/buttons/ButtonSimpleElevated.svelte';
	import CardGrid from '$lib/components/layout/CardGrid.svelte';

	$: albums = $albumCache.items.map(album => ({
		title: album.name,
		subtitle: album.artists?.map(a => a.name).join(", "),
		image: album?.images?.at(0)?.url,
		fallbackSvg: "album",
		href: null
	}))

</script>

<div class="header">
	<h2>Albums</h2>
	<ButtonSimpleElevated on:click={reloadSavedAlbumsWithTracks}>Reload Albums</ButtonSimpleElevated>
</div>

<CardGrid cards={albums} />

<style lang="scss">

	.header {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
</style>
