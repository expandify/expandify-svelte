<script lang="ts">
  import { page } from '$app/stores';
  import {DiscIcon, ListIcon, UsersIcon, MusicIcon, UserIcon, FileTextIcon} from 'svelte-feather-icons'
  import MenuEntry from "../elements/IconLink.svelte";
  import {libraryStore} from "../../stores/library.js";

  let currentPath: string
  let currentLibrary: string
  $: currentPath = $page.url.pathname
  $: currentLibrary = $libraryStore.currentLibrary

  let entries: {text: string, href: string, icon: any}[]
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
      text: "Libraries",
      href: "/library",
      icon: FileTextIcon
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
    },
    {
      text: "User",
      href: "/library/user",
      icon: UserIcon
    }
  ]

</script>

<div class="menu-list">
  {#each entries as entry}

    <div class="menu-entry" class:current={currentPath === entry.href}>
      <MenuEntry href={entry.href} icon={entry.icon}>
        {entry.text}
      </MenuEntry>
    </div>
  {/each}
</div>

<style lang="scss">

  .menu-list {
    position: sticky;
    top: 0;
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-content: center;
    background-color: var(--bg-main-900);

    .current {
      background-color: var(--bg-main-100);
      border-bottom: 0.2rem solid var(--accent);
    }

    .menu-entry:hover {
      background-color: var(--bg-main-100);
    }

    .menu-entry {
      flex-grow: 1;
      padding: 1rem;
    }

  }

</style>