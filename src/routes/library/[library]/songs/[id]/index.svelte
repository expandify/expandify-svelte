<script>
  import InfoView from "../../../../../lib/client/components/elements/InfoView.svelte";


  export let track = {}


  const albumStruct = {
    "Name": track.name,
    "Artists": track.artists.map(artist => artist.name).join(", "),
    "Disc Number": track.disc_number,
    "Explicit": track.explicit,
    "Spotify Api Link": track.href,
    "Popularity": track.popularity,
    "Spotify Id": track.id,
    "Album": track.album.name,
    "Track Number": track.track_number
  }

</script>

<InfoView infoStruct="{albumStruct}"/>