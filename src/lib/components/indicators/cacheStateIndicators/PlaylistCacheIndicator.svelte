<script lang="ts">
	import { playlistCache } from "$lib/stores/cache";
	import CacheStateIndicator from "./CacheStateIndicator.svelte";

</script>

<CacheStateIndicator 
  cache={playlistCache}
  loadingMessage="Loading Playlists"
  loadingSecondaryMessage="Loading Songs Of Playlists"
  finishedMessage="Successfully Loaded All Playlists"
  errorMessage="Error Loading Playlists"
/>