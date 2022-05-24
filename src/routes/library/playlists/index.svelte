<script>
  import {onMount} from "svelte";
  import {playlistStore, STORE_STATUS} from "../../../stores/library.js";
  import LoadingBar from "../../../lib/client/components/elements/LoadingBar.svelte";
  import Table from "../../../lib/client/components/elements/Table.svelte";
  import {populatePagedStore} from "../../../lib/client/library/store.js";

  export let items = null

  let headers = ["Name", "Id", "Owner", "Public", "Collaborative", "Tracks"]
  let playlists; // This is not necessary, but otherwise the IDE is not happy
  $: playlists = $playlistStore.items.map(playlist => ({
    "Name": playlist.name,
    "Id": playlist.id,
    "Owner": playlist.owner.display_name,
    "Public": playlist.public,
    "Collaborative": playlist.collaborative,
    "Tracks": playlist.tracks.total
  }))

  onMount(() => {
    populatePagedStore(playlistStore, "/library/playlists/__data.json", {items: items})
  })
</script>




{#if $playlistStore.status !== STORE_STATUS.FINISHED}
  <LoadingBar name="Albums" max="{$playlistStore.total}" current="{$playlistStore.items.length}" status={$playlistStore.status}/>
{:else}
  <Table headers={headers} items={playlists}/>
{/if}

