<script lang="ts">
  import {LibraryStatus} from "../../shared/types/Library";
  import LoadingDots from "./LoadingDots.svelte";
  import {formatDate} from "../../shared/helpers";
  import SearchBar from "./SearchBar.svelte";

  export let title: string
  export let state: number
  export let lastUpdated: string | null
  export let search: string = ""

</script>

<div class="top">
  <h1>{title}</h1>
  {#if state === LibraryStatus.ready && lastUpdated !== null}
    <span>Last Updated: {formatDate(lastUpdated)}</span>
  {/if}
  <SearchBar searchIn="{title.toLowerCase()}" bind:search={search}/>
</div>

{#if state === LibraryStatus.loading}
  <LoadingDots>{title}</LoadingDots>
{:else if state === LibraryStatus.error}
  <h1>{Error}</h1>
  Please try to reload the page.
{:else}
  <slot/>
{/if}


<style lang="scss">

  .top {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
  }

</style>


