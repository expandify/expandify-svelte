<script lang="ts">
	import ButtonSimpleElevated from '$lib/components/buttons/ButtonSimpleElevated.svelte';
	import TrackTable from '$lib/components/layout/TrackTable.svelte';
	import { Spotify } from '$lib/data/spotify';
	import { dependencies } from '$lib/stores/dependencies';
	import { tracks } from '$lib/stores/library/tracks';

	dependencies.onlyTracksNeeded()
</script>

<div class="header">
	<h2>Tracks - {$tracks.tracks.length}</h2>
	<ButtonSimpleElevated on:click={Spotify.Track.loadSavedToStore}>Reload Tracks</ButtonSimpleElevated>
</div>


<TrackTable tracks={$tracks.tracks}/>

<style lang="scss">
	.header {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
</style>
