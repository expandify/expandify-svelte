<script lang="ts">
	import Grid from '$lib/components/layout/Grid.svelte';
	import { albums } from '$lib/stores/library/albums';
	import {dependencies} from "$lib/stores/dependencies";
	import Card from '$lib/components/common/Card.svelte';

	
	dependencies.onlyAlbumsNeeded();
	

</script>


<h2>Albums - {$albums.albums.length}</h2>


<Grid>
	{#each $albums.albums as album}
		<Card card={album}/>
	{/each}
</Grid>

<style lang="scss">

</style>
