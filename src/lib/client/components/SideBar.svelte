<script lang="ts">
  import IconLink from "./IconLink.svelte";
  import {FolderIcon, HomeIcon, UploadCloudIcon, UserIcon, ShuffleIcon} from "svelte-feather-icons";
  import {backupCurrentLibrary} from "../functions/library";
  import {session} from "$app/stores";
  import {onMount} from "svelte";
  import {LibrarySimplified, LibraryType} from "../../types/Library";
  import {formatDate} from "../functions/helpers";

  let libraries: LibrarySimplified[] = []

  onMount(async () => {
    const response = await fetch($session.BASE_URL + "/libraries/__data.json",)
    const json: {libraries: LibrarySimplified[]} = await response.json()
    libraries = json.libraries
  })

  async function saveLibrary() {
    await backupCurrentLibrary($session)
    const response = await fetch($session.BASE_URL + "/libraries/__data.json",)
    const json: {libraries: LibrarySimplified[]} = await response.json()
    libraries = json.libraries
  }
</script>

<aside class="sidebar">
  <IconLink href="/" icon={HomeIcon}>Home</IconLink>
  <IconLink href="/libraries/user" icon={UserIcon}>User</IconLink>
  <hr class="divider">
  <IconLink href="/libraries/current/albums" icon={FolderIcon}>Current library</IconLink>
  <span class="snapshot-title">Snapshots:</span>
  <div class="snapshots scrollbar">
    {#each libraries as lib}
      {#if lib.type !== LibraryType.current}
        <a href="/libraries/{lib.id}/albums">{formatDate(lib.date)}</a>
        <a href="/libraries/{lib.id}/albums">{formatDate(lib.date)}</a>
        <a href="/libraries/{lib.id}/albums">{formatDate(lib.date)}</a>
        <a href="/libraries/{lib.id}/albums">{formatDate(lib.date)}</a>
        <a href="/libraries/{lib.id}/albums">{formatDate(lib.date)}</a>
        <a href="/libraries/{lib.id}/albums">{formatDate(lib.date)}</a>
        <a href="/libraries/{lib.id}/albums">{formatDate(lib.date)}</a>
        <a href="/libraries/{lib.id}/albums">{formatDate(lib.date)}</a>
      {/if}
    {/each}
  </div>
  <hr class="divider bottom">
  <IconLink icon={UploadCloudIcon} func={saveLibrary}>Save Library</IconLink>
  <IconLink icon={ShuffleIcon} href="/libraries">Compare Libraries</IconLink>
</aside>

<style lang="scss">

  .sidebar {
    width: 15rem;
    height: 100vh;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    gap: 1.3rem;
    justify-content: flex-start;
    background-color: var(--bg-main-900);
    top: 0;
    position: sticky;
    padding: 1.5rem 1.5rem 10rem;
    border-right: 0.1rem solid var(--bg-main-100);

    .divider {
      border-radius: 5rem;
      border-color: var(--bg-main-100);
      border-width: 0.05rem;
      height: 0.05rem;
      border-style: solid;
      width: 100%;
      background-color: var(--bg-main-100);
      margin-top: 1rem;
      margin-bottom: 1rem;
    }

    .bottom {
      margin-top: auto;
    }

    .snapshot-title {
      margin-top: 1rem;
      opacity: 30%;
    }
    .snapshots {
      height: 100%;
      display: flex;
      flex-direction: column;
      gap: 1rem;
      overflow-y: scroll;
    }

    /* Firefox */
    .scrollbar {
      scrollbar-width: 0.5rem;
      scrollbar-color:  var(--bg-main-50) rgba(0,0,0,0);
    }

    /* Chrome, Edge, and Safari */
    .scrollbar::-webkit-scrollbar {
      width: 0.5rem;
    }

    .scrollbar::-webkit-scrollbar-track {
      background: rgba(0,0,0,0);
    }

    .scrollbar::-webkit-scrollbar-thumb {
      background-color: var(--bg-main-50);
      border-radius: 1rem;
      border: 3px solid rgba(0,0,0,0);
    }
  }

</style>