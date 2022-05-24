<script>
  import {songStore, STORE_STATUS} from "../../../stores/library.js";
  import LoadingBar from "../../../lib/client/components/elements/LoadingBar.svelte";
  import Table from "../../../lib/client/components/elements/Table.svelte";
  import {msToTime} from "../../../lib/shared/helpers.js";
  import {onMount} from "svelte";
  import {populatePagedStore} from "../../../lib/client/library/store.js";

  export let items = null

  let headers = ["Name", "Artists", "Duration", "Album", "Popularity", "Explicit"]
  let songs; // This is not necessary, but otherwise the IDE is not happy
  $: songs = $songStore.items.map(song => ({
    "Name": song.track.name,
    "Artists":  song.track.artists.map(artist => artist.name).join(),
    "Duration": msToTime(song.track.duration_ms),
    "Album": song.track.album.name,
    "Popularity": song.track.popularity,
    "Explicit": song.track.explicit,
  }))

  onMount(() => {
    populatePagedStore(songStore, "/library/songs/__data.json", {items: items})
  })

</script>




{#if $songStore.status !== STORE_STATUS.FINISHED}
  <LoadingBar name="Albums" max="{$songStore.total}" current="{$songStore.items.length}" status={$songStore.status}/>
{:else}
  <Table headers={headers} items={songs}/>
{/if}

