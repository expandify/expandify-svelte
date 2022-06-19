<script lang="ts">
  import {page, session} from '$app/stores';
  import {DiscIcon, ListIcon, UsersIcon, MusicIcon, RefreshCwIcon} from 'svelte-feather-icons'
  import MenuEntry from "./IconLink.svelte";
  import {
    albumStore,
    artistStore,
    libraryStore,
    playlistStore,
    trackStore
  } from "$lib/client/stores/library";
  import {LibrarySimplified, LibraryStatus, LibraryType} from "$lib/types/Library";
  import {refreshCurrentLibrary} from "$lib/client/functions/library";

  let currentPath: string
  $: currentPath = $page.url.pathname + $page.url.search
  let loading: boolean
  $: loading = $albumStore.status === LibraryStatus.loading
    || $artistStore.status === LibraryStatus.loading
    || $playlistStore.status === LibraryStatus.loading
    || $trackStore.status === LibraryStatus.loading

  let entries: { text: string, href: string, icon: any }[]
  $: entries = [
    {
      text: "Albums",
      href: calcHref("albums", $libraryStore.activeLibrary, $libraryStore.compareTo),
      icon: DiscIcon
    },
    {
      text: "Artist",
      href: calcHref("artists", $libraryStore.activeLibrary, $libraryStore.compareTo),
      icon: UsersIcon
    },
    {
      text: "Playlists",
      href: calcHref("playlists", $libraryStore.activeLibrary, $libraryStore.compareTo),
      icon: ListIcon
    },
    {
      text: "Tracks",
      href: calcHref("tracks", $libraryStore.activeLibrary, $libraryStore.compareTo),
      icon: MusicIcon
    }
  ]

  function calcHref(endpoint: string, activeLib: LibrarySimplified, compareTo: LibrarySimplified | null) {
    let href = `/libraries/${activeLib?.id}/${endpoint}`
    if (compareTo) {
      return `${href}?compare-to=${compareTo.id}`
    }
    return href
  }

</script>
<div class="menu">
  <div class="menu-list">
    {#each entries as entry}
      <div class="menu-entry" class:current={currentPath === entry.href}>
        <MenuEntry href={entry.href} icon={entry.icon}>
          {entry.text}
        </MenuEntry>
      </div>
    {/each}
    {#if $page.params?.library === LibraryType.current}
      {#if  !loading}
        <div class="menu-entry refresh-library" on:click={() => refreshCurrentLibrary($session)}>
          <MenuEntry icon={RefreshCwIcon}>Refresh Library</MenuEntry>
        </div>
      {:else}
        <div class="menu-entry refresh-library">
          <RefreshCwIcon class="rotate"/>Refreshing
        </div>
      {/if}
    {/if}
  </div>
</div>


<style lang="scss">
  .menu {
    width: 100%;
    position: sticky;
    top: 0;
    background-color: var(--bg-main-900);
    border-bottom: 0.1rem solid var(--bg-main-100);
    .menu-list {
      display: flex;
      flex-direction: row;
      align-content: center;

      .current {
        //background-color: var(--bg-main-100);
        border-bottom: 0.2rem solid var(--accent);
      }

      .menu-entry {
        flex-grow: 1;
        max-width: 10rem;
        padding: 1rem;
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: flex-start;
        height: fit-content;
        :global(.rotate) {
          margin-right: 1rem;
          animation: rotation 2s infinite linear
        }
      }
    }
    .refresh-library {
      justify-self: flex-end;
      margin-left: auto;
    }
  }

  @keyframes rotation {
    from {
      transform: rotate(0deg);
    }
    to {
      transform: rotate(359deg);
    }
  }
</style>