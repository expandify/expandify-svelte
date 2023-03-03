<script lang="ts">
	import TrackTable from '$lib/components/layout/TrackTable.svelte';
	import { dependencies } from '$lib/stores/dependencies';
	import { tracks } from '$lib/stores/library/tracks';

	dependencies.onlyTracksNeeded()
</script>


<h2>Tracks - {$tracks.tracks.length}</h2>


<TrackTable tracks={$tracks.tracks} showImage={true} showAddedAt={true} />

<style lang="scss">

</style>
