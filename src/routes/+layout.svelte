<script lang="ts">
  import "@picocss/pico/css/pico.css";
  import "../app.css";
	import { browser } from "$app/environment";
	import { goto } from "$app/navigation";
	import NavBar from "$lib/components/NavBar.svelte";
	import { state } from "$lib/stores/state";
	import LoadingIndicator from "$lib/components/LoadingIndicator.svelte";
	import { albumCache, artistCache, Cache, playlistCache, trackCache } from "$lib/stores/cache";

  if (browser && !$state.authenticated) {
    goto("/");
  }
  let indicators: {[id: string]: { max: number | null, value: number | null, name: string }} = {};

  function createIndicator<T extends SpotifyData>(cache: Cache.Data<T>, nameLoading: string, nameLoadingSecondary: string) {
    if ( cache.state !== Cache.State.Loading && cache.state !== Cache.State.LoadingSecondary) {
      delete indicators[cache.name];
      // This makes sure svelte notices the update on the indicators
      indicators = indicators;
      return;
    }

    let name = cache.state === Cache.State.Loading ? nameLoading : nameLoadingSecondary;
    indicators[cache.name] = {name: name, max: cache.totalItems, value: cache.loadedItems};
  }


  // This calls the functions everytime a store changes.
  $: createIndicator($albumCache, "Albums", "Songs Of Albums")
  $: createIndicator($artistCache, "Artists", "Artists")
  $: createIndicator($playlistCache, "Playlists", "Songs Of Playlists")
  $: createIndicator($trackCache, "Tracks", "Tracks")  

</script>

<div class="page">
  {#if $state.authenticated}
    <div class="navbar">
      <NavBar/>
    </div>
  {/if}
  <main class="content">
    <slot></slot>
    <div class="floating-indicators">
      {#each Object.values(indicators) as indicator}
        <LoadingIndicator max={indicator.max} value={indicator.value} name={indicator.name}/>
      {/each}      
    </div>    
  </main>
</div>

<style lang="scss">
  .page {
    padding: 2rem;
    display: flex;
    flex-direction: row; 

    .navbar {
      padding-right: 2rem;
      width: 20rem;
    }

    .content {
      width: 100%;

      .floating-indicators {
        position: fixed;
        bottom: 0rem;
        right: 0rem;        
      }
    }
    
  }

  
</style>