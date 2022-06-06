<script>
  import Table from "../../../../lib/components/elements/Table.svelte";

  export let items
  export const spotifyHeaders = null


  let headers = ["Name", "Artists", "Label", "Tracks"]
  let albums = items.map(album => ({
    "Name": album.name,
    "Artists": album.artists.map(artist => artist.name).join(),
    "Label": album.label,
    "Tracks": album.total_tracks,
    "id": album.id
  }))

</script>

<Table headers={headers} items={albums}/>



