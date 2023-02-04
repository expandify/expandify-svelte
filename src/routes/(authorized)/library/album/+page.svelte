<script lang="ts">
	import ButtonSimpleElevated from '$lib/components/buttons/ButtonSimpleElevated.svelte';
	import AlbumCard from '$lib/components/cards/AlbumCard.svelte';
	import Grid from '$lib/components/layout/Grid.svelte';
	import { loadSavedAlbumsWithTracks } from '$lib/spotify/api/albums';
	import { albumStore } from '$lib/stores/library/albums';
	

</script>

<div class="header">
	<h2>Albums - {$albumStore.albums.length}</h2>
	<ButtonSimpleElevated on:click={loadSavedAlbumsWithTracks}>Reload Albums</ButtonSimpleElevated>
</div>

<Grid>
	{#each $albumStore.albums as album}
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
