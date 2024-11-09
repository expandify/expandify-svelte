<script lang="ts">
    import TrackTable from '$lib/components/layout/TrackTable.svelte';
    import {dependencies} from '$lib/stores/dependencies';
    import {tracks} from '$lib/stores/library/tracks';
    import {spotifyPersistence} from "$lib/services/spotify/spotify-persistance";
    import { RefreshCcw } from 'lucide-svelte';

    dependencies.onlyTracksNeeded()
</script>


<h2 class="header">
    <span>Tracks - {$tracks.tracks.length}</span>
    <button class="button" onclick={() => spotifyPersistence.reloadSavedTracks()} title="Reload Library">
        <RefreshCcw />
    </button>
</h2>

<TrackTable tracks={$tracks.tracks} showImage={true} showAddedAt={true}/>

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
