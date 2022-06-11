<script lang="ts">
  import {formatDate, imageSelector, msToTime} from "$lib/shared/helpers";
  import {page} from "$app/stores";
  import {songStore} from "$lib/client/stores/library";
  import LibraryView from "$lib/client/components/elements/LibraryView.svelte";
  import SongView from "$lib/client/components/elements/SongView.svelte";
  import type {Track} from "$lib/shared/types/Track";
  import type {TableTrack} from "$lib/shared/types/TableTrack";


  export let items: Track[] = []
  export let last_updated: string
  let search: string = ""
  $songStore.songs = items
  $: songs = parseSongs($songStore.songs)

  function parseSongs(songs: Track[]): TableTrack[] {
    return songs.map(value =>
        ({
          name: value.name,
          artists: value.artists.map(artist => ({id: artist.id, name: artist.name})),
          album: {id: value.album.id, name: value.album.name},
          added_at: value.added_at ? formatDate(value.added_at) : "",
          duration: msToTime(value.duration_ms),
          image: imageSelector(value.album?.images),
          id: value.id
        }))
  }


</script>

<LibraryView title="Artists" state="{$songStore.state}" lastUpdated="{last_updated}" bind:search>
  <SongView songs="{songs}" hrefBasePath="{$page.url.href}" search="{search}"/>
</LibraryView>
