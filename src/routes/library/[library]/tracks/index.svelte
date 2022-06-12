<script lang="ts">
  import {page} from "$app/stores";
  import {trackStore} from "$lib/client/stores/library";
  import LibraryView from "../../../../lib/client/components/LibraryView.svelte";
  import TrackView from "../../../../lib/client/components/TrackView.svelte";
  import type {LibraryTrack} from "$lib/shared/types/Track";
  import {LibraryItem} from "../../../../lib/shared/types/Library";
  import {toTableTrack} from "$lib/shared/types/TableTrack";


  export let libraryItem: LibraryItem<LibraryTrack[]>
  $trackStore = libraryItem
  let search: string = ""
  $: tracks = $trackStore.item.map(toTableTrack)
</script>

<LibraryView title="Tracks" state="{$trackStore.status}" lastUpdated="{$trackStore.last_updated}" bind:search>
  <TrackView tracks="{tracks}" hrefBasePath="{$page.url.href}" search="{search}"/>
</LibraryView>
