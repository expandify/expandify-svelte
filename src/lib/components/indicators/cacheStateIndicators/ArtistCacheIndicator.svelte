<script lang="ts">
	import { artistCache } from "$lib/stores/cache";
	import CacheStateIndicator from "./CacheStateIndicator.svelte";

</script>

<CacheStateIndicator 
  cache={artistCache}
  loadingMessage="Loading Artists"
  loadingSecondaryMessage="Loading Artists"
  finishedMessage="Successfully Loaded All Artists"
  errorMessage="Error Loading Artists"
/>