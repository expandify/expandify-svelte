<script>
  import Table from "../../../../lib/components/elements/Table.svelte";
  import {msToTime} from "../../../../shared/helpers.js";


  export let items
  export const spotifyHeaders = null

  let headers = ["Name", "Artists", "Duration", "Album", "Popularity", "Explicit"]
  let songs = items.map(song => ({
    "Name": song.name,
    "Artists":  song.artists.map(artist => artist.name).join(),
    "Duration": msToTime(song.duration_ms),
    "Album": song.name,
    "Popularity": song.popularity,
    "Explicit": song.explicit,
    "id": song.id
  }))

</script>

<Table headers={headers} items={songs}/>

