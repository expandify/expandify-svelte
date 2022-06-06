<script>
  import {LogOutIcon} from 'svelte-feather-icons'
  import MenuEntry from "./MenuEntry.svelte";
  import {
    userStore, albumStore, artistStore,
    playlistStore, songStore, libraryStore,
    emptyLibraryStore, STORE_STATUS
  } from "../../../stores/library.js";
  import {goto} from "$app/navigation";

  function logout() {

    emptyLibraryStore(albumStore)
    emptyLibraryStore(artistStore)
    emptyLibraryStore(playlistStore)
    emptyLibraryStore(songStore)
    emptyLibraryStore(libraryStore)
    emptyLibraryStore(userStore, {user: {}, status: STORE_STATUS.READY})

    goto("/api/auth/logout")
  }
</script>


<div on:click={logout}>
  <MenuEntry icon={LogOutIcon}>Sign Out</MenuEntry>
</div>

