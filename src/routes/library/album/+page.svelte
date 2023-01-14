<script lang="ts">
	import { reloadSavedAlbumsWithTracks } from "$lib/spotify/album";
	import { albumCache } from "$lib/stores/cache";
</script>

<p>Albums</p>

<button on:click={reloadSavedAlbumsWithTracks}>Refresh Albums</button>  


<div class="albums">
  {#each $albumCache as album}

  <h1>{album.name}</h1>

  {#each album.tracks as track}
  <span>{track.name}</span>  
  {/each}  
    
  {/each}
</div>

<style lang="scss">
  .albums {
    display: flex;
    flex-direction: column;
  }
</style>