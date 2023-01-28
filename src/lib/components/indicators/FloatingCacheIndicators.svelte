<script lang="ts">
	import { albumStore } from "$lib/stores/library/albums";
	import { artistStore } from "$lib/stores/library/artists";
	import { playlistStore } from "$lib/stores/library/playlists";	
	import { trackStore } from "$lib/stores/library/tracks";
	import { userStore } from "$lib/stores/library/user";
  import StoreStateIndicator from "./StoreStateIndicator.svelte"

</script>


<div class="floating-indicators">

  <StoreStateIndicator 
    state={$userStore.status}
    successMsg="Playlist Ready" 
    loadingMsg="Loading Playlists" 
    errorMsg="Error Loading Playlists" 
    value={null}
    max={null}/>


  <StoreStateIndicator 
    state={$albumStore.status}
    successMsg="Albums Ready" 
    loadingMsg="Loading Albums" 
    errorMsg="Error Loading Albums" 
    value={$albumStore.albums.length} 
    max={$albumStore.total_albums} />


  <StoreStateIndicator 
    state={$artistStore.status}
    successMsg="Artists Ready" 
    loadingMsg="Loading Artists" 
    errorMsg="Error Loading Artists" 
    value={$artistStore.artists.length} 
    max={$artistStore.total_artists || null} />


  <StoreStateIndicator 
    state={$playlistStore.status}
    successMsg="Playlists Ready" 
    loadingMsg="Loading Playlists" 
    errorMsg="Error Loading Playlists" 
    value={$playlistStore.playlists.length} 
    max={$playlistStore.total_playlists} />


  <StoreStateIndicator 
    state={$trackStore.status}
    successMsg="Tracks Ready" 
    loadingMsg="Loading Tracks" 
    errorMsg="Error Loading Tracks" 
    value={$trackStore.tracks.length} 
    max={$trackStore.total_tracks} />
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