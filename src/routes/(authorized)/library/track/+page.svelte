<script lang="ts">
	import ButtonSimpleElevated from '$lib/components/buttons/ButtonSimpleElevated.svelte';
	import TrackTable from '$lib/components/layout/TrackTable.svelte';
	import { Tracks, trackStore } from '$lib/stores/library/tracks';
</script>

<div class="header">
	<h2>Tracks</h2>
	<ButtonSimpleElevated on:click={Tracks.loadAll}>Reload Tracks</ButtonSimpleElevated>
</div>


<TrackTable tracks={$trackStore.tracks}/>

<style lang="scss">
	.header {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
</style>
