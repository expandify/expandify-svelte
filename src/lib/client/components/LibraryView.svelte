<script lang="ts">
  import {LibraryItem, LibraryStatus} from "../../types/Library";
  import LoadingDots from "./LoadingDots.svelte";
  import SearchBar from "./SearchBar.svelte";
  import {libraryStore} from "../stores/library.js";
  import LibraryInfo from "./LibraryInfo.svelte";

  export let title: string
  export let search: string = ""
  export let libItem: LibraryItem<any>


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

{#if libItem.status === LibraryStatus.loading}
  <LoadingDots>Loading {title}</LoadingDots>
{:else if libItem.status === LibraryStatus.error}
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


