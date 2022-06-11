<script lang="ts">
  import {page, session} from "$app/stores";
  import {RefreshCwIcon} from "svelte-feather-icons"
  import IconLink from "$lib/client/components/elements/IconLink.svelte";
  import {LibraryStatus, LibraryType} from "$lib/shared/types/Library";
  import LoadingDots from "$lib/client/components/elements/LoadingDots.svelte";
  import {albumStore, artistStore, playlistStore, trackStore} from "$lib/client/stores/library";
  import {libraryStore} from "$lib/client/stores/library.js";

  libraryStore.update(value => ({
    ...value,
    currentLibrary: $page.params?.library || LibraryType.current
  }))

  let loading: boolean
  $: loading = $albumStore.state === LibraryStatus.loading
      || $artistStore.state === LibraryStatus.loading
      || $playlistStore.state === LibraryStatus.loading
      || $trackStore.state === LibraryStatus.loading

  async function refreshLibrary() {
    await fetch($session.BASE_URL + "/library/update-current/albums", {method: 'POST'})
    await fetch($session.BASE_URL + "/library/update-current/artists", {method: 'POST'})
    await fetch($session.BASE_URL + "/library/update-current/playlists", {method: 'POST'})
    await fetch($session.BASE_URL + "/library/update-current/tracks", {method: 'POST'})
    $albumStore.state = LibraryStatus.loading
    $artistStore.state = LibraryStatus.loading
    $playlistStore.state = LibraryStatus.loading
    $trackStore.state = LibraryStatus.loading

    const albumInterval = setInterval(async () => {
      const albums = await fetch($session.BASE_URL + "/library/current/albums/__data.json")
      $albumStore.state = albums.status
      if (albums.status !== LibraryStatus.loading) {
        clearInterval(albumInterval)
        $albumStore.albums = (await albums.json()).items
      }
    }, 1000)

    const artistInterval = setInterval(async () => {
      const artists = await fetch($session.BASE_URL + "/library/current/artists/__data.json")
      $artistStore.state = artists.status
      if (artists.status !== LibraryStatus.loading) {
        clearInterval(artistInterval)
        $artistStore.artists = (await artists.json()).items
        $artistStore.state = LibraryStatus.ready
      }
    }, 1000)

    const playlistInterval = setInterval(async () => {
      const playlists = await fetch($session.BASE_URL + "/library/current/playlists/__data.json")
      $playlistStore.state = playlists.status
      if (playlists.status !== LibraryStatus.loading) {
        clearInterval(playlistInterval)
        $playlistStore.playlists = (await playlists.json()).items
      }
    }, 1000)

    const trackInterval = setInterval(async () => {
      const tracks = await fetch($session.BASE_URL + "/library/current/tracks/__data.json")
      $trackStore.state = tracks.status
      if (tracks.status !== LibraryStatus.loading) {
        clearInterval(trackInterval)
        $trackStore.tracks = (await tracks.json()).items
      }
    }, 1000)

  }


</script>

<div class="library-head">
  Library: {$libraryStore.currentLibrary}
  {#if loading}
    <LoadingDots>Library</LoadingDots>
  {:else if $libraryStore.currentLibrary === LibraryType.current }
    <div on:click={refreshLibrary}>
      <IconLink icon={RefreshCwIcon}>Refresh Library</IconLink>
    </div>
  {/if}
</div>

<slot/>


<style lang="scss">

  .library-head {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    margin-bottom: 2rem;
  }
</style>
