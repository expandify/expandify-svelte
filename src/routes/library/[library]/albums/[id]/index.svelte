<script>
  import InfoView from "../../../../../lib/components/elements/InfoView.svelte";
  export let album = {}


  const albumStruct = {
    "Album type": album.album_type,
    "Artists": album.artists.map(artist => artist.name).join(", "),
    "Available Markets": album.available_markets,
    "Copyright": album.copyrights.map(c => c.text).join(", "),
    "Genres": album.genres,
    "Spotify Api Link": album.href,
    "Images": album.images,
    "Label": album.lable,
    "Name": album.name,
    "Popularity": album.popularity,
    "Release Date": album.release_date,
    "Release Date Precision": album.release_date_precision,
    "Total Tracks": album.total_tracks,
    "Tracks": album.tracks.items.map(track => track.name).join(", ")
  }


</script>

<InfoView infoStruct="{albumStruct}"/>