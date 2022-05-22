<script>
  import {songs} from "../../stores/library/songs.js";
  import LoadingBar from "../../lib/client/components/elements/LoadingBar.svelte";
  import {STORE_STATUS} from "../../lib/client/library/store.js";
  import Table from "../../lib/client/components/elements/Table.svelte";
  import {msToTime} from "../../lib/shared/helpers.js";

  let headers = ["Name", "Artists", "Duration", "Album", "Popularity", "Explicit"]
  let sizes = ["10rem", "8rem", "8rem", "16rem", "6rem", "5rem"]
  let items; // This is not necessary, but otherwise the IDE is not happy
  $: items = $songs.items.map(song => ({
    "Name": song.track.name,
    "Artists":  song.track.artists.map(artist => artist.name).join(),
    "Duration": msToTime(song.track.duration_ms),
    "Album": song.track.album.name,
    "Popularity": song.track.popularity,
    "Explicit": song.track.explicit,
  }))


</script>




{#if $songs.status !== STORE_STATUS.FINISHED}
  <LoadingBar name="Songs" max="{$songs.total}" current="{$songs.items.length}" status={STORE_STATUS.FETCHING}/>
{:else}
  <Table headers={headers} items={items} sizes="{sizes}"/>
{/if}

