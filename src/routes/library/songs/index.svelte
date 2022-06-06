<script>
  import {songStore, STORE_STATUS} from "../../../stores/library.js";
  import LoadingBar from "../../../lib/components/elements/LoadingBar.svelte";
  import Table from "../../../lib/components/elements/Table.svelte";
  import {msToTime} from "../../../shared/helpers.js";
  import {onMount} from "svelte";
  import {populatePagedStore} from "../../../lib/library/store.js";
  import {page} from "$app/stores";

  export let items = null
  export let spotifyHeaders = null

  let headers = ["Name", "Artists", "Duration", "Album", "Popularity", "Explicit"]
  let songs; // This is not necessary, but otherwise the IDE is not happy
  $: songs = $songStore.items.map(song => ({
    "Name": song.track.name,
    "Artists":  song.track.artists.map(artist => artist.name).join(),
    "Duration": msToTime(song.track.duration_ms),
    "Album": song.track.album.name,
    "Popularity": song.track.popularity,
    "Explicit": song.track.explicit,
    "id": song.track.id
  }))

  onMount(() => {

    populatePagedStore(songStore, "/library/songs/__data.json", {items: items})
  })

</script>




{#if $songStore.status !== STORE_STATUS.FINISHED}
  <LoadingBar name="Songs" max="{$songStore.total}" current="{$songStore.items.length}" status={$songStore.status}/>
{:else}
  <Table headers={headers} items={songs} gotoPath="/library/songs"/>
{/if}

