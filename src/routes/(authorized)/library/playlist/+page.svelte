<script lang="ts">
    import CardGrid from '$lib/components/layout/CardGrid.svelte';
    import {dependencies} from '$lib/stores/dependencies';
    import {playlists} from '$lib/stores/library/playlists';
    import {spotifyPersistence} from "$lib/services/spotify/spotify-persistance";
    import { RefreshCcw } from 'lucide-svelte';

    dependencies.onlyPlaylistsNeeded();
</script>


<h2 class="header">
    <span>Playlists - {$playlists.playlists.length}</span>
    <button class="button" onclick={() => spotifyPersistence.reloadPlaylists()} title="Reload Library">
        <RefreshCcw />
    </button>
</h2>
<CardGrid cards={$playlists.playlists}/>

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
