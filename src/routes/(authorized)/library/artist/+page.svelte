<script lang="ts">	
	import CardGrid from '$lib/components/layout/CardGrid.svelte';
	import { dependencies } from '$lib/stores/dependencies';
	import { artists } from '$lib/stores/library/artists';
  import { spotifyPersistence } from "$lib/services/spotify/spotify-persistance";
  import Svg from "$lib/components/common/Svg.svelte";

	dependencies.onlyArtistsNeeded();
</script>


<h2 class="header">
  <span>Artists - {$artists.artists.length}</span>
  <button class="button" on:click={() => spotifyPersistence.reloadFollowedArtists()} title="Reload Library">
    <Svg name={"reload"} />
  </button>
</h2>
<CardGrid cards={$artists.artists}/>

<style lang="scss">
  .header {
    display: flex;
    flex-direction: row;
    .button {
      display: flex;
      align-items: center;
      background-color: inherit;
      border: none;
      fill: var(--text-subdued);
      cursor: pointer;
      width: 2.5rem;
    }
    .button:hover {
      fill: var(--text-base);
    }
  }
</style>
