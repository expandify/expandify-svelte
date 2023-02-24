<script lang="ts">
	import { Spotify } from "$lib/data/spotify";
  import { dependencies } from "$lib/stores/dependencies";
	import { albums } from "$lib/stores/library/albums";
	import { artists } from "$lib/stores/library/artists";
	import { playlists } from "$lib/stores/library/playlists";
	import { tracks } from "$lib/stores/library/tracks";
	import { user } from "$lib/stores/library/user";
	import { fade } from "svelte/transition";
	import ButtonSimple from "../buttons/ButtonSimple.svelte";
	import LoadingText from "./LoadingText.svelte";

  $: anyNeeded = 
        ($dependencies.albums && ($albums.loading || $albums.error)) || 
        ($dependencies.artists && ($artists.loading || $artists.error)) || 
        ($dependencies.playlists && ($playlists.loading || $playlists.error)) || 
        ($dependencies.tracks && ($tracks.loading || $tracks.error)) || 
        ($dependencies.user && ($user.loading || $user.error)); 

</script>

{#if anyNeeded}
<div class="overlay" transition:fade>
  {#if $dependencies.albums }
  <LoadingText 
    title={"Albums"} 
    current={$albums.albums.length} 
    total={$albums.total} 
    loading={$albums.loading} 
    error={$albums.error !== null}>

    {#if $albums.error}
      <ButtonSimple on:click={Spotify.Album.loadSavedToStore}><p>Retry</p></ButtonSimple>  
    {/if}    
  </LoadingText>

  {/if}

  {#if $dependencies.artists}
  <LoadingText 
    title={"Artists"} 
    current={$artists.artists.length} 
    total={$artists.total} 
    loading={$artists.loading} 
    error={$artists.error !== null} >

    {#if $artists.error}
      <ButtonSimple on:click={Spotify.Artist.loadFollowedToStore}><p>Retry</p></ButtonSimple>  
    {/if}    
  </LoadingText>
  {/if}

  {#if $dependencies.playlists}
  <LoadingText 
    title={"Playlists"} 
    current={$playlists.playlists.length} 
    total={$playlists.total} 
    loading={$playlists.loading} 
    error={$playlists.error !== null} >

    {#if $playlists.error}
      <ButtonSimple on:click={Spotify.Playlist.loadAllToStore}><p>Retry</p></ButtonSimple>  
    {/if}    
  </LoadingText>
  {/if}

  {#if $dependencies.tracks}
  <LoadingText 
    title={"Tracks"} 
    current={$tracks.tracks.length} 
    total={$tracks.total} 
    loading={$tracks.loading} 
    error={$tracks.error !== null} >

    {#if $tracks.error}
      <ButtonSimple on:click={Spotify.Track.loadSavedToStore}><p>Retry</p></ButtonSimple>  
    {/if}    
  </LoadingText>
  {/if}

  {#if $dependencies.user}
  <LoadingText 
    title={"User"} 
    loading={$user.loading} 
    error={$user.error !== null} >

    {#if $user.error}
      <ButtonSimple on:click={Spotify.User.loadToStore}><p>Retry</p></ButtonSimple>  
    {/if}    
  </LoadingText>
  {/if}
</div>
{/if}



<style lang="scss">
  .overlay {
    position: fixed;   
    box-sizing: border-box;
    background-color: rgba(0, 0, 0, 0.97);
    padding: 2rem 2rem 2rem 2rem;    
    top: 0;
    bottom: 0;
    width: 100%;
    display: flex;
    justify-content: center;
    gap: 2rem;    
    flex-direction: column;
    z-index: 2;
  }
</style>
