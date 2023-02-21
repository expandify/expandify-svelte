<script lang="ts">
	import ButtonSimpleElevated from '$lib/components/buttons/ButtonSimpleElevated.svelte';
	import ArtistCard from '$lib/components/cards/ArtistCard.svelte';
	import Grid from '$lib/components/layout/Grid.svelte';
	import { Spotify } from '$lib/data/spotify';
	import { loadFollowedArtists } from '$lib/services/spotify/api/artists';
	import { dependencies } from '$lib/stores/dependencies';
	import { artists } from '$lib/stores/library/artists';

	dependencies.onlyArtistsNeeded();
</script>

<div class="header">
	<h2>Artists - {$artists.artists.length}</h2>
	<ButtonSimpleElevated on:click={Spotify.Artist.loadFollowedToStore}>Reload Artists</ButtonSimpleElevated>
</div>

<Grid>
	{#each $artists.artists as artist}
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
