<script lang="ts">
	import { indicators, Indicator } from "$lib/stores/indicators";
	import AnnouncementIndicator from "./AnnouncementIndicator.svelte";
	import ErrorIndicator from "./ErrorIndicator.svelte";
	import LoadingIndicator from "./LoadingIndicator.svelte";
	import SuccessIndicator from "./SuccessIndicator.svelte";
	import WarningIndicator from "./WarningIndicator.svelte";
</script>


<div class="floating-indicators">
  {#each $indicators as indicator}
  {#if indicator.type === Indicator.Type.Announcement }
  <AnnouncementIndicator message={indicator.message}/>

  {:else if indicator.type === Indicator.Type.Error }
  <ErrorIndicator message={indicator.message}/>

  {:else if indicator.type === Indicator.Type.Loading }
  <LoadingIndicator message={indicator.message} value={indicator.value} max={indicator.max}/>

  {:else if indicator.type === Indicator.Type.Success }
  <SuccessIndicator message={indicator.message}/>

  {:else if indicator.type === Indicator.Type.Warning }
  <WarningIndicator message={indicator.message}/>

  {/if}
  {/each}
</div>

<style lang="scss">
  .floating-indicators {
    position: fixed;
    bottom: 0;
    right: 0;

    display: flex;
    flex-direction: column;
    gap: 1rem;

    margin: 1rem;
  }
</style>