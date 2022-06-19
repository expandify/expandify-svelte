<script lang="ts">
  import {LibrarySimplified, LibraryType} from "$lib/types/Library";
  import {formatDate} from "$lib/client/functions/helpers";
  import ComparingDiv from "./ComparingDiv.svelte";

  export let title: string
  export let library: LibrarySimplified

</script>

<div class="info current">
  <ComparingDiv libraryId={library?.id}>
    <h1>{title}</h1>
  </ComparingDiv>
  

  <div class="item">
    <span class="key">Library Type: </span>
    <span class="value">{library?.type}</span>
  </div>
  <div class="item">
    <span class="key">Library ID: </span>
    <span class="value">{library?.id}</span>
  </div>
  <div class="item">
    <span class="key">
      {#if library?.type === LibraryType.current}
        Last Refreshed:
      {:else if library?.type === LibraryType.snapshot}
        Snapshot from:
      {/if}
    </span>
    <span class="value">{formatDate(library?.date)}</span>
  </div>
</div>

<style lang="scss">

  .info {
    width: 100%;
    display: flex;
    flex-direction: column;
    gap: 0.5rem;

    .item {
      width: 100%;
      display: flex;
      flex-direction: row;

      align-items: center;
      justify-content: flex-start;

      .key {
        width: 11rem;
      }
      .value {
        width: 15rem;
      }

    }
  }

</style>