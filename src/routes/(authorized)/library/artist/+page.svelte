<script lang="ts">	
	import CardGrid from '$lib/components/layout/CardGrid.svelte';
	import { dependencies } from '$lib/stores/dependencies';
	import { artists } from '$lib/stores/library/artists';

	dependencies.onlyArtistsNeeded();
</script>


<h2>Artists - {$artists.artists.length}</h2>
<CardGrid cards={$artists.artists}/>

<style lang="scss">

</style>
