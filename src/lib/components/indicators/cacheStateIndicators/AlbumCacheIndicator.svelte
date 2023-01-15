<script lang="ts">
	import { albumCache } from "$lib/stores/cache";
	import CacheStateIndicator from "./CacheStateIndicator.svelte";

</script>

<CacheStateIndicator 
  cache={albumCache}
  loadingMessage="Loading Albums"
  loadingSecondaryMessage="Loading Songs Of Albums"
  finishedMessage="Successfully Loaded All Albums"
  errorMessage="Error Loading Albums"
/>