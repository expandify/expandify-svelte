<script>
  import Table from "../../../../lib/components/elements/Table.svelte";


  export let items
  export const spotifyHeaders = null

  let headers = ["Name", "Genres", "Popularity", "Followers"]
  let artists = items.map(artist => ({
    "Name": artist.name,
    "Genres": artist.genres.join(", "),
    "Popularity": artist.popularity,
    "Followers": artist.followers?.total,
    "id": artist.id
  }))

</script>

<Table headers={headers} items={artists}/>


