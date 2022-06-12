<script lang="ts">
  import {LibraryStatus, LibraryType} from "../../types/Library";
  import LoadingDots from "./LoadingDots.svelte";
  import {formatDate} from "../functions/helpers";
  import SearchBar from "./SearchBar.svelte";
  import {libraryStore} from "../stores/library.js";
  import LibraryInfo from "./LibraryInfo.svelte";

  export let title: string
  export let state: number
  export let lastUpdated: string | null
  export let search: string = ""

  let libraryDate: string
  // Parameter for reactivity
  $: libraryDate = calcLibraryDate($libraryStore)

  function calcLibraryDate(_) {
    if (!$libraryStore.compareTo && $libraryStore.activeLibrary.type === LibraryType.current) {
      return "Current Library. Last Refreshed: " + formatDate(lastUpdated)
    }
    if (!$libraryStore.compareTo) {
      return "Library Snapshot from: " + formatDate(lastUpdated)
    }
  }

</script>

<div class="libraries" >
  <LibraryInfo title="Active Library" library={$libraryStore.activeLibrary}/>

  {#if $libraryStore.compareTo}
    <LibraryInfo title="Compare To" library={$libraryStore.compareTo}/>
  {/if}
</div>
<div class="top">

  <h1>{title}</h1>
  <SearchBar searchIn="{title.toLowerCase()}" bind:search={search}/>
</div>

{#if state === LibraryStatus.loading}
  <LoadingDots>Loading {title}</LoadingDots>
{:else if state === LibraryStatus.error}
  <h1>{Error}</h1>
  Please try to reload the page.
{:else}
  <slot />
{/if}


<style lang="scss">

  .libraries {
    display: flex;
    flex-direction: row;

  }
  .top {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
  }

</style>


