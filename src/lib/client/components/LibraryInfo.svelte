<script lang="ts">
  import {LibrarySimplified, LibraryType} from "../../types/Library.js";
  import {formatDate} from "../functions/helpers.js";
  import {libraryStore} from "../stores/library.js";

  export let title: string
  export let library: LibrarySimplified

</script>

<div class="info current">
  <h1 class:accent-green={library.id === $libraryStore.activeLibrary.id}
      class:accent-blue={library.id === $libraryStore.compareTo?.id}
      class:accent-info={$libraryStore.compareTo?.id}>{title}</h1>

  <div class="item">
    <span class="key">Library Type: </span>
    <span class="value">{library.type}</span>
  </div>
  <div class="item">
    <span class="key">Library ID: </span>
    <span class="value">{library.id}</span>
  </div>
  <div class="item">
    <span class="key">
      {#if library.type === LibraryType.current}
        Last Refreshed:
      {:else if library.type === LibraryType.snapshot}
        Snapshot from:
      {/if}
    </span>
    <span class="value">{formatDate(library.date)}</span>
  </div>
</div>

<style lang="scss">

  .accent-info {
    color: var(--accent);
  }

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