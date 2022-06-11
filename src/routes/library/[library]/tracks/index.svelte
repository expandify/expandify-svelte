<script lang="ts">
  import {formatDate, imageSelector, msToTime} from "$lib/shared/helpers";
  import {page} from "$app/stores";
  import {trackStore} from "$lib/client/stores/library";
  import LibraryView from "$lib/client/components/elements/LibraryView.svelte";
  import TrackView from "$lib/client/components/elements/TrackView.svelte";
  import type {Track} from "$lib/shared/types/Track";
  import type {TableTrack} from "$lib/shared/types/TableTrack";


  export let items: Track[] = []
  export let last_updated: string | null
  let search: string = ""
  $trackStore.tracks = items
  $: tracks = parseTracks($trackStore.tracks)

  function parseTracks(tracks: Track[]): TableTrack[] {
    return tracks.map(value =>
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

<LibraryView title="Tracks" state="{$trackStore.state}" lastUpdated="{last_updated}" bind:search>
  <TrackView tracks="{tracks}" hrefBasePath="{$page.url.href}" search="{search}"/>
</LibraryView>
