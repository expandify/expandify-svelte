<script>
  import Table from "../../../../lib/components/elements/Table.svelte";

  export let items
  export const spotifyHeaders = null

  let headers = ["Name", "Id", "Owner", "Public", "Collaborative", "Tracks"]
  let playlists = items.map(playlist => ({
    "Name": playlist.name,
    "Id": playlist.id,
    "Owner": playlist.owner.display_name,
    "Public": playlist.public,
    "Collaborative": playlist.collaborative,
    "Tracks": playlist.tracks.total,
    "id": playlist.id
  }))

</script>


<Table headers={headers} items={playlists}/>

