<script lang="ts">
    import {dependencies} from '$lib/stores/dependencies';
    import {tracks} from '$lib/stores/library/tracks';
    import {spotifyPersistence} from "$lib/services/spotify/spotify-persistance";
    import { RefreshCcw } from 'lucide-svelte';
    import DataTable from '$lib/components/datatable/DataTable.svelte';
    import { savedTracksFilterFunc, savedTrackColumns } from '$lib/components/datatable/columns/tracks';

    dependencies.onlyTracksNeeded()
</script>


<h2 class="header">
    <span>Tracks - {$tracks.tracks.length}</span>
    <button class="button" onclick={() => spotifyPersistence.reloadSavedTracks()} title="Reload Library">
        <RefreshCcw />
    </button>
</h2>

<!-- <TrackTable tracks={$tracks.tracks} showImage={true} showAddedAt={true}/> -->
<DataTable data={$tracks.tracks}
           columConfig={savedTrackColumns}
           filterFunc={savedTracksFilterFunc} />