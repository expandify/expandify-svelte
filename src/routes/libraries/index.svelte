
<script lang="ts">
  import {LibrarySimplified} from "../../lib/types/Library";
  import LibraryPicker from "../../lib/client/components/LibraryPicker.svelte";
  import {libraryStore} from "../../lib/client/stores/library";
  import {fade} from "svelte/transition";

  export let libraries: LibrarySimplified[] = []

  let librariesLeft: LibrarySimplified[]
  $: librariesLeft = libraries.filter(value => !right || value.id !== right.id)
  let librariesRight: LibrarySimplified[]
  $: librariesRight = libraries.filter(value => !left || value.id !== left.id)

  let left: LibrarySimplified | null = null
  let right: LibrarySimplified | null = null

  let url: string
  $: url = getUrl(left, right)

  function getUrl(l: LibrarySimplified | null, r: LibrarySimplified | null) {
    return (l && r) ? `/libraries/${l.id}/artists?compare-to=${r.id}` : "";
  }

  function handleCompare() {
    $libraryStore.activeLibrary = left
    $libraryStore.compareTo = right
  }
</script>

<div class="top" in:fade>
  <a href="{url}" class="compare" class:disabled={url === ""} on:click={handleCompare}>Compare</a>
</div>
<div class="side-by-side" in:fade>
  <LibraryPicker libraries={librariesLeft} bind:selected={left}/>
  <LibraryPicker libraries={librariesRight} bind:selected={right}/>
</div>

<style lang="scss">

  .top {
    text-align: center;
    width: 100%;
    margin-bottom: 5rem;
    .compare {
      margin-left: auto;
      background-color: var(--bg-main-100);
      padding: 1rem;
      border-radius: 0.5rem;
      border: 0.1rem solid rgba(0,0,0,0);
    }
    .compare:hover {
      border: 0.1rem solid var(--accent);
    }

    .disabled {
      pointer-events: none;
      opacity: 40%;
    }
  }

  .side-by-side {
    padding: 1rem 15rem;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }
</style>