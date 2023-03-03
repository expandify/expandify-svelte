<script lang="ts">
	import TrackRow from '$lib/components/layout/table/TrackRow.svelte';
	import { dependencies } from '$lib/stores/dependencies';
	import { tracks } from '$lib/stores/library/tracks';

	dependencies.onlyTracksNeeded()
</script>


<h2>Tracks - {$tracks.tracks.length}</h2>


<TrackRow track={null} showImage={true}></TrackRow>  
{#each $tracks.tracks as track, i}
<TrackRow track={track} showImage={true} index={i + 1}></TrackRow>          
{/each} 

<style lang="scss">

</style>
