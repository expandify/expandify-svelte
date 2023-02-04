<script lang="ts">
	import ButtonSimpleElevated from '$lib/components/buttons/ButtonSimpleElevated.svelte';
	import TrackTable from '$lib/components/layout/TrackTable.svelte';
	import { loadSavedTracks } from '$lib/spotify/api/tracks';
	import { trackStore } from '$lib/stores/library/tracks';
</script>

<div class="header">
	<h2>Tracks - {$trackStore.tracks.length}</h2>
	<ButtonSimpleElevated on:click={loadSavedTracks}>Reload Tracks</ButtonSimpleElevated>
</div>


<TrackTable tracks={$trackStore.tracks}/>

<style lang="scss">
	.header {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
</style>
