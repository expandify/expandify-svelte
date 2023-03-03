<script lang="ts">	
	import Card from '$lib/components/common/Card.svelte';
	import Grid from '$lib/components/layout/Grid.svelte';
	import { dependencies } from '$lib/stores/dependencies';
	import { artists } from '$lib/stores/library/artists';

	dependencies.onlyArtistsNeeded();
</script>


<h2>Artists - {$artists.artists.length}</h2>


<Grid>
	{#each $artists.artists as artist}
		<Card card={artist}/>
	{/each}
</Grid>

<style lang="scss">

</style>
