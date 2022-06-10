<script>
  import {Library} from "../../../shared/classes/Library";
  import LoadingDots from "./LoadingDots.svelte";
  import {formatDate} from "../../../shared/helpers";
  import SearchBar from "./SearchBar.svelte";

  export let title
  export let state
  export let lastUpdated
  export let search = ""

</script>

<div class="top">
  <h1>{title}</h1>
  {#if lastUpdated !== null}
    <span>Last Updated: {formatDate(lastUpdated)}</span>
  {/if}
  <SearchBar searchIn="{title.toLowerCase()}" bind:search={search}/>
</div>

{#if state === Library.Status.loading}
  <LoadingDots>{title}</LoadingDots>
{:else if state === Library.Status.error}
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

    .search {
      background-color: var(--bg-main-100);
      display: flex;
      flex-direction: row;
      align-items: center;

      border-radius: 0.2rem;
      padding: 0.2rem;

      input {
        box-sizing: border-box;
        border: none;
        background-color: var(--bg-main-100);
        color: var(--text-700);
        margin-left: 0.4rem;
      }

      input:focus {
        outline: none;
      }
    }
  }


</style>


