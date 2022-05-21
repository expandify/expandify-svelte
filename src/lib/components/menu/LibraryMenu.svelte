<script>
  import {DiscIcon, ListIcon, UsersIcon, MusicIcon, UserIcon, FileTextIcon} from 'svelte-feather-icons'
  import MenuList from "./MenuList.svelte";

  const entries = [
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

<MenuList items={entries}>
</MenuList>


<style lang="scss">

</style>