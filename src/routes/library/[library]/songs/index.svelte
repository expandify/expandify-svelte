<script>
  import {formatDate, imageSelector, msToTime} from "../../../../lib/shared/helpers";
  import {page} from "$app/stores";
  import {songStore} from "../../../../lib/client/stores/library";
  import LibraryView from "../../../../lib/client/components/elements/LibraryView.svelte";
  import SongView from "../../../../lib/client/components/elements/SongView.svelte";


  export let items = []
  export let last_updated
  let search = ""
  $songStore.songs = items
  $: songs = parseSongs($songStore.songs)

  function parseSongs(songs) {
    return songs.map(value =>
        ({
          name: value.name,
          artists: value.artists.map(artist => artist.name).join(),
          album: value.album.name,
          added_at: formatDate(value.added_at),
          duration: msToTime(value.duration_ms),
          image: imageSelector(value.album.images),
          id: value.id
        }))
  }


</script>

<LibraryView title="Artists" state="{$songStore.state}" lastUpdated="{last_updated}" bind:search>
  <SongView songs="{songs}" hrefBasePath="{$page.url.href}" search="{search}"/>
</LibraryView>
