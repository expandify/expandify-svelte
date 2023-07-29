<script lang="ts">
	import CardGrid from '$lib/components/layout/CardGrid.svelte';
	import { albums } from '$lib/stores/library/albums';
	import {dependencies} from "$lib/stores/dependencies";
  import { spotifyPersistence } from "$lib/services/spotify/spotify-persistance";
  import Svg from "$lib/components/common/Svg.svelte";


  dependencies.onlyAlbumsNeeded();
	

</script>


<h2 class="header">
  <span>Albums - {$albums.albums.length}</span>
  <button class="button" on:click={() => spotifyPersistence.reloadSavedAlbums()} title="Reload Library">
    <Svg name={"reload"} />
  </button>
</h2>

<CardGrid cards={$albums.albums}/>

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
