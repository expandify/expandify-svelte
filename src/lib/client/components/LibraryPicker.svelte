<script lang="ts">
  import {LibrarySimplified, LibraryType} from "../../types/Library";
  import {formatDate} from "../functions/helpers";
  import IconLink from "./IconLink.svelte";
  import {CheckSquareIcon, SquareIcon} from "svelte-feather-icons";

  export let libraries: LibrarySimplified[] = []
  export let selected: LibrarySimplified | null

  let libs: {id: string, text: string, library: LibrarySimplified}[]
  $: libs = libraries.map(l => ({
    id: l.id,
    text: l.type === LibraryType.current ? "Current" : formatDate(l.date),
    library: l

  }))

  function handleClick(lib: {id: string, text: string, library: LibrarySimplified}) {
    selected = (selected && selected.id === lib.id) ? null : lib.library;
  }
</script>

<div class="list">
  {#each libs as lib}
    <div on:click={() => handleClick(lib)} class:selected={selected && selected.id === lib.id}>
      {#if selected && selected.id === lib.id}
        <IconLink icon={CheckSquareIcon}>{lib.text}</IconLink>
      {:else}
        <IconLink icon={SquareIcon}>{lib.text}</IconLink>
      {/if}
    </div>
  {/each}
</div>

<style lang="scss">

  .list {
    display: flex;
    flex-direction: column;
    gap: 2rem;

  }
</style>