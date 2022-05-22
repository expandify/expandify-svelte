<script>
  import {playlists} from "../../stores/playlists.js";
  import LoadingBar from "../../lib/client/components/elements/LoadingBar.svelte";
  import { STORE_STATUS } from "../../stores/builder.js";
  import Table from "../../lib/client/components/elements/Table.svelte";

  let headers = ["Name", "Id", "Owner", "Public", "Collaborative", "Tracks"]
  let sizes = ["10rem", "16rem", "8rem", "4rem", "8rem", "5rem"]
  let items; // This is not necessary, but otherwise the IDE is not happy
  $: items = $playlists.items.map(playlist => ({
    "Name": playlist.name,
    "Id": playlist.id,
    "Owner": playlist.owner.display_name,
    "Public": playlist.public,
    "Collaborative": playlist.collaborative,
    "Tracks": playlist.tracks.total
  }))
</script>




{#if $playlists.status !== STORE_STATUS.FINISHED}
  <LoadingBar name="Albums" max="{$playlists.total}" current="{$playlists.items.length}" status={STORE_STATUS.FETCHING}/>
{:else}
  <Table headers={headers} items={items} sizes="{sizes}"/>
{/if}

