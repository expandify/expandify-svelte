<script>
  import {DiscIcon, ListIcon, UsersIcon, MusicIcon, UserIcon, FileTextIcon} from 'svelte-feather-icons'
  import MenuEntry from "./MenuEntry.svelte";

  $: entries = [
    {
      text: "Albums",
      href: "/library/albums",
      icon: DiscIcon
    },
    {
      text: "Artist",
      href: "/library/artists",
      icon: UsersIcon
    },
    {
      text: "Libraries",
      href: "/library/libraries",
      icon: FileTextIcon
    },
    {
      text: "Playlists",
      href: "/library/playlists",
      icon: ListIcon
    },
    {
      text: "Songs",
      href: "/library/songs",
      icon: MusicIcon
    },
    {
      text: "User",
      href: "/library/user",
      icon: UserIcon
    }
  ]

</script>

<ul class="menu-list">
  {#each entries as entry}
    <li>
      <MenuEntry href={entry.href} icon={entry.icon}>
        {entry.text}
      </MenuEntry>
    </li>
  {/each}
</ul>

<style lang="scss">

  ul.menu-list {

    margin-top: 2rem;
    padding: 0;
    font-weight: bold;

    li {
      list-style-type: none;
      display: flex;
      flex-direction: row;
      justify-content: flex-start;
      padding-left: 1rem;
      height: 2rem;
      margin-bottom: 0.6rem;
    }
  }

</style>