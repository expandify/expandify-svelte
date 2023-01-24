<script lang="ts">
	import { reloadSavedTracks } from '$lib/spotify/track';
	import { trackCache } from '$lib/stores/cache';
</script>

<p>Tracks</p>

<button on:click={reloadSavedTracks}>Refresh Tracks</button>

<div class="tracks">
	{#each $trackCache.items as track}
		<p>
			{track.name}
		</p>
	{/each}
</div>

<style lang="scss">
	.tracks {
		display: flex;
		flex-direction: column;
	}
</style>
