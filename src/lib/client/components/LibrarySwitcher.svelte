<script lang="ts">
  import {page, session} from '$app/stores';
  import {DiscIcon, ListIcon, UsersIcon, MusicIcon, RefreshCwIcon} from 'svelte-feather-icons'
  import MenuEntry from "./IconLink.svelte";
  import {
    albumStore,
    artistStore,
    libraryStore,
    playlistStore,
    refreshCurrentLibrary,
    trackStore
  } from "../stores/library.js";
  import {LibraryStatus, LibraryType} from "../../types/Library.js";

  let currentPath: string
  let currentLibrary: string
  $: currentPath = $page.url.pathname
  $: currentLibrary = $libraryStore.currentLibrary

  let loading: boolean
  $: loading = $albumStore.status === LibraryStatus.loading
    || $artistStore.status === LibraryStatus.loading
    || $playlistStore.status === LibraryStatus.loading
    || $trackStore.status === LibraryStatus.loading

  let entries: { text: string, href: string, icon: any }[]
  $: entries = [
    {
      text: "Albums",
      href: `/library/${currentLibrary}/albums`,
      icon: DiscIcon
    },
    {
      text: "Artist",
      href: `/library/${currentLibrary}/artists`,
      icon: UsersIcon
    },
    {
      text: "Playlists",
      href: `/library/${currentLibrary}/playlists`,
      icon: ListIcon
    },
    {
      text: "Tracks",
      href: `/library/${currentLibrary}/tracks`,
      icon: MusicIcon
    }
  ]

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
    {#if $libraryStore.currentLibrary === LibraryType.current && !loading}
      <div class="menu-entry refresh-library" on:click={() => refreshCurrentLibrary($session)}>
        <MenuEntry icon={RefreshCwIcon}>Refresh Library</MenuEntry>
      </div>
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
      }
    }
    .refresh-library {
      justify-self: flex-end;
      margin-left: auto;
    }
  }
</style>