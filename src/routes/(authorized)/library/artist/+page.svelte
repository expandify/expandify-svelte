<script lang="ts">
	import ButtonSimpleElevated from '$lib/components/buttons/ButtonSimpleElevated.svelte';
	import ArtistCard from '$lib/components/cards/ArtistCard.svelte';
	import Grid from '$lib/components/layout/Grid.svelte';
	import { loadFollowedArtists } from '$lib/spotify/api/artists';
	import { artistStore } from '$lib/stores/library/artists';

</script>

<div class="header">
	<h2>Artists - {$artistStore.artists.length}</h2>
	<ButtonSimpleElevated on:click={loadFollowedArtists}>Reload Artists</ButtonSimpleElevated>
</div>

<Grid>
	{#each $artistStore.artists as artist}
		<ArtistCard artist={artist}/>
	{/each}
</Grid>

<style lang="scss">

	.header {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
</style>
