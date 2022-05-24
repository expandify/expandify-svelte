<script>
  import {onMount} from "svelte";
  import {albumStore, STORE_STATUS} from "../../../stores/library.js";
  import Table from "../../../lib/client/components/elements/Table.svelte";
  import LoadingBar from "../../../lib/client/components/elements/LoadingBar.svelte";
  import {populatePagedStore} from "../../../lib/client/library/store.js";

  export let items = null


  let headers = ["Name", "Artists", "Label", "Tracks"]
  let albums; // This is not necessary, but otherwise the IDE is not happy
  $: albums = $albumStore.items.map(album => ({
    "Name": album.album.name,
    "Artists": album.album.artists.map(artist => artist.name).join(),
    "Label": album.album.label,
    "Tracks": album.album.total_tracks,
    "id": album.album.id
  }))

  onMount(() => {
    populatePagedStore(albumStore, "/library/albums/__data.json", {items: items})
  })

</script>

{#if $albumStore.status !== STORE_STATUS.FINISHED}
  <LoadingBar name="Albums" max="{$albumStore.total}" current="{$albumStore.items.length}" status={$albumStore.status}/>
{:else}
  <Table headers={headers} items={albums}/>
{/if}


