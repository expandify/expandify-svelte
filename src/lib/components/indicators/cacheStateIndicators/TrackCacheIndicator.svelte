<script lang="ts">
	import { trackCache } from "$lib/stores/cache";
	import CacheStateIndicator from "./CacheStateIndicator.svelte";

</script>

<CacheStateIndicator 
  cache={trackCache}
  loadingMessage="Loading Track"
  loadingSecondaryMessage="Loading Track"
  finishedMessage="Successfully Loaded All Track"
  errorMessage="Error Loading Track"
/>