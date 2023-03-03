<script lang="ts">
	import Card from '$lib/components/common/Card.svelte';
	import Grid from '$lib/components/layout/Grid.svelte';
	import { dependencies } from '$lib/stores/dependencies';
	import { playlists } from '$lib/stores/library/playlists';

	dependencies.onlyPlaylistsNeeded();
</script>


<h2>Playlists - {$playlists.playlists.length}</h2>


<Grid>
	{#each $playlists.playlists as playlist}
		<Card card={playlist}/>
	{/each}
</Grid>

<style lang="scss">

</style>
