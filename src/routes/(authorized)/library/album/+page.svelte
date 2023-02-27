<script lang="ts">
	import ButtonSimpleElevated from '$lib/components/buttons/ButtonSimpleElevated.svelte';
	import AlbumCard from '$lib/components/cards/AlbumCard.svelte';
	import Grid from '$lib/components/layout/Grid.svelte';
	import { Spotify } from '$lib/data/spotify';
	import { albums } from '$lib/stores/library/albums';
	import {dependencies} from "$lib/stores/dependencies";

	
	dependencies.onlyAlbumsNeeded();
	

</script>

<div class="header">
	<h2>Albums - {$albums.albums.length}</h2>
	<ButtonSimpleElevated on:click={Spotify.Album.loadSavedToStore}>Reload Albums</ButtonSimpleElevated>
</div>

<Grid>
	{#each $albums.albums as album}
		<AlbumCard album={album}/>
	{/each}
</Grid>

<style lang="scss">

	.header {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
</style>
