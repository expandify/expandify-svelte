<script lang="ts">
	import ButtonSimpleElevated from '$lib/components/buttons/ButtonSimpleElevated.svelte';
	import TrackTable from '$lib/components/layout/TrackTable.svelte';
	import { reloadSavedTracks } from '$lib/spotify/track';
	import { trackCache } from '$lib/stores/cache';
</script>

<div class="header">
	<h2>Tracks</h2>
	<ButtonSimpleElevated on:click={reloadSavedTracks}>Reload Tracks</ButtonSimpleElevated>
</div>


<TrackTable tracks={$trackCache.items}/>

<style lang="scss">
	.header {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
</style>
