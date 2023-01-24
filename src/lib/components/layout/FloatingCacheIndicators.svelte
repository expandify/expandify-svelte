<script lang="ts">
  import AlbumCacheIndicator from "../indicators/cacheStateIndicators/AlbumCacheIndicator.svelte";
	import ArtistCacheIndicator from "../indicators/cacheStateIndicators/ArtistCacheIndicator.svelte";
	import PlaylistCacheIndicator from "../indicators/cacheStateIndicators/PlaylistCacheIndicator.svelte";
	import TrackCacheIndicator from "../indicators/cacheStateIndicators/TrackCacheIndicator.svelte";
</script>

<div class="floating-indicators">
  <AlbumCacheIndicator/>
  <ArtistCacheIndicator/>
  <PlaylistCacheIndicator/>
  <TrackCacheIndicator/>
</div>

<style lang="scss">
  .floating-indicators {
    position: fixed;
    bottom: 0;
    right: 0;

    display: flex;
    flex-direction: column;
    gap: 1rem;

    margin: 1rem;
  }
</style>