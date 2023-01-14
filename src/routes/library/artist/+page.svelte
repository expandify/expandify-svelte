<script lang="ts">
	import { reloadFollowedArtists } from "$lib/spotify/artist";
	import { artistCache } from "$lib/stores/cache";
</script>

<p>Artists</p>

<button on:click={reloadFollowedArtists}>Refresh Artists</button>  


<div class="artists">
  {#each $artistCache.items as artist}
  <p>
    {artist.name}
  </p>
    
  {/each}
</div>

<style lang="scss">
  .artists {
    display: flex;
    flex-direction: column;
  }
</style>