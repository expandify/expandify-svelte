<script lang="ts">
	import ButtonSimpleElevated from '$lib/components/buttons/ButtonSimpleElevated.svelte';
	import CardGrid from '$lib/components/layout/CardGrid.svelte';
	import { Artists, artistStore } from '$lib/stores/library/artists';

	$: artists = $artistStore.artists.map(artist => ({
		title: artist.name,
		subtitle: "Artist",
		image: artist.images?.at(0)?.url,
		fallbackSvg: "artist",
		href: null
	}))

</script>

<div class="header">
	<h2>Artists</h2>
	<ButtonSimpleElevated on:click={Artists.loadAll}>Reload Artists</ButtonSimpleElevated>
</div>

<CardGrid cards={artists} imgRounded={"full"}/>

<style lang="scss">

	.header {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
</style>
