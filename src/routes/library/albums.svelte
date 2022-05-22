<script>
  import {albums} from "../../stores/albums.js";
  import Table from "../../lib/client/components/elements/Table.svelte";
  import LoadingBar from "../../lib/client/components/elements/LoadingBar.svelte";
  import { STORE_STATUS } from "../../stores/builder.js";

  let headers = ["Name", "Artists", "Label", "Tracks"]
  let sizes = ["16rem", "16rem", "16rem", "4rem"]
  let items; // This is not necessary, but otherwise the IDE is not happy
  $: items = $albums.items.map(album => ({
    "Name": album.album.name,
    "Artists": album.album.artists.map(artist => artist.name).join(),
    "Label": album.album.label,
    "Tracks": album.album.total_tracks
  }))


</script>

{#if $albums.status !== STORE_STATUS.FINISHED}
  <LoadingBar name="Albums" max="{$albums.total}" current="{$albums.items.length}" status={STORE_STATUS.FETCHING}/>
{:else}
  <Table headers={headers} items={items} sizes="{sizes}"/>
{/if}


