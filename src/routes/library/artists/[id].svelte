<script>
  import InfoView from "../../../lib/client/components/elements/InfoView.svelte";


  export let artist = {}


  const albumStruct = {
    "Name": artist.name,
    "Followers": artist.followers.total,
    "Genres": artist.genres,
    "Spotify Api Link": artist.href,
    "Images": artist.images,
    "Popularity": artist.popularity,
    "Spotify Id": artist.id
  }

</script>

<InfoView infoStruct="{albumStruct}"/>