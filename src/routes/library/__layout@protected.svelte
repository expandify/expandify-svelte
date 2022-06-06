<script>
import LibrarySwitcher from "../../lib/components/page-elements/LibrarySwitcher.svelte";
import PageLoader from "../../lib/components/elements/PageLoader.svelte";
import {navigating} from "$app/stores";

</script>

<div>
  <LibrarySwitcher/>
  <div class="slot">
    {#if $navigating}
      <PageLoader />
    {:else}
      <slot/>
    {/if}
  </div>
</div>

<style lang="scss">
  .slot {
    padding: 2rem;
  }
</style>
