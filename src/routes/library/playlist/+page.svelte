<script lang="ts">
	import { reloadUserPlaylists } from "$lib/spotify/playlist";
	import { playlistCache } from "$lib/stores/cache";
</script>

<p>Playlists</p>

<button on:click={reloadUserPlaylists}>Refresh Playlists</button>  


<div class="playlists">
  {#each $playlistCache as playlist}

  <h1>{playlist.name}</h1>

    {#each playlist.tracks as track}
    <span>{track.track.name}</span>  
    {/each}  
    
  {/each}
</div>

<style lang="scss">
  .playlists {
    display: flex;
    flex-direction: column;
  }
</style>