<script>
  import {page, session} from "$app/stores";
  import {config} from "../../../lib/client/stores/config";
  import {RefreshCwIcon} from "svelte-feather-icons"
  import IconLink from "../../../lib/client/components/elements/IconLink.svelte";
  import {Library} from "../../../lib/shared/classes/Library";
  import LoadingDots from "../../../lib/client/components/elements/LoadingDots.svelte";
  import {albumStore, artistStore, playlistStore, songStore} from "../../../lib/client/stores/library";

  config.update(value => ({
    ...value,
    currentLibrary: value.currentLibrary || $page.params?.library || Library.Type.current
  }))


  $: loading = $albumStore.state === Library.Status.loading
      || $artistStore.state === Library.Status.loading
      || $playlistStore.state === Library.Status.loading
      || $songStore.state === Library.Status.loading

  async function refreshLibrary() {

    await fetch($session.BASE_URL + "/library/update-current/albums", {method: 'POST'})
    await fetch($session.BASE_URL + "/library/update-current/artists", {method: 'POST'})
    await fetch($session.BASE_URL + "/library/update-current/playlists", {method: 'POST'})
    await fetch($session.BASE_URL + "/library/update-current/songs", {method: 'POST'})
    $albumStore.state = Library.Status.loading
    $artistStore.state = Library.Status.loading
    $playlistStore.state = Library.Status.loading
    $songStore.state = Library.Status.loading

    const albumInterval = setInterval(async () => {
      const albums = await fetch($session.BASE_URL + "/library/current/albums/__data.json")
      $albumStore.state = albums.status
      if (albums.status !== Library.Status.loading) {
        clearInterval(albumInterval)
        $albumStore.albums = (await albums.json()).items
      }
    }, 1000)

    const artistInterval = setInterval(async () => {
      const artists = await fetch($session.BASE_URL + "/library/current/artists/__data.json")
      $artistStore.state = artists.status
      if (artists.status !== Library.Status.loading) {
        clearInterval(artistInterval)
        $artistStore.artists = (await artists.json()).items
        $artistStore.state = Library.Status.ready
      }
    }, 1000)

    const playlistInterval = setInterval(async () => {
      const playlists = await fetch($session.BASE_URL + "/library/current/playlists/__data.json")
      $playlistStore.state = playlists.status
      if (playlists.status !== Library.Status.loading) {
        clearInterval(playlistInterval)
        $playlistStore.playlists = (await playlists.json()).items
      }
    }, 1000)

    const songInterval = setInterval(async () => {
      const songs = await fetch($session.BASE_URL + "/library/current/songs/__data.json")
      $songStore.state = songs.status
      if (songs.status !== Library.Status.loading) {
        clearInterval(songInterval)
        $songStore.songs = (await songs.json()).items
      }
    }, 1000)

  }


</script>

<div class="library-head">
  Library: {$config.currentLibrary}
  {#if loading}
    <LoadingDots/>
  {:else if $config.currentLibrary === Library.Type.current }
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
