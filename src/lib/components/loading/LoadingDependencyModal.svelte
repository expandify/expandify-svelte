<script lang="ts">
	import { dependencies } from "$lib/stores/dependencies";
	import { albums } from "$lib/stores/library/albums";
	import { artists } from "$lib/stores/library/artists";
	import { playlists } from "$lib/stores/library/playlists";
	import { tracks } from "$lib/stores/library/tracks";
	import { user } from "$lib/stores/library/user";
	import { fade } from "svelte/transition";
	import LoadingText from "./LoadingText.svelte";

  $: anyNeeded = 
        ($dependencies.albums && $albums.loading) || 
        ($dependencies.artists && $artists.loading) || 
        ($dependencies.playlists && $playlists.loading) || 
        ($dependencies.tracks && $tracks.loading) || 
        ($dependencies.user && $user.loading); 

  $: console.log($albums.loading);

</script>

{#if anyNeeded}
<div class="overlay" transition:fade>
  {#if $dependencies.albums }
  <LoadingText 
    title={"Albums"} 
    current={$albums.albums.length} 
    total={$albums.total} 
    loading={$albums.loading} 
  />
  {/if}

  {#if $dependencies.artists}
  <LoadingText 
    title={"Artists"} 
    current={$artists.artists.length} 
    total={$artists.total} 
    loading={$artists.loading} 
  />
  {/if}

  {#if $dependencies.playlists}
  <LoadingText 
    title={"Playlists"} 
    current={$playlists.playlists.length} 
    total={$playlists.total} 
    loading={$playlists.loading} 
  />
  {/if}

  {#if $dependencies.tracks}
  <LoadingText 
    title={"Tracks"} 
    current={$tracks.tracks.length} 
    total={$tracks.total} 
    loading={$tracks.loading} 
  />
  {/if}

  {#if $dependencies.user}
  <LoadingText 
    title={"User"} 
    loading={$user.loading} 
  />
  {/if}
</div>
{/if}



<style lang="scss">
  .overlay {
    position: fixed;   
    box-sizing: border-box;
    background-color: rgba(0, 0, 0, 0.9);
    padding: 2rem 2rem 2rem 2rem;    
    top: 0;
    bottom: 0;
    width: 100%;
    display: flex;
    gap: 2rem;    
    flex-direction: column;
    z-index: 2;
  }
</style>
