<script lang="ts">
  import type {Track} from "$lib/types/Track";

  export let track: Track


  const albumStruct = {
    "Name": track?.name,
    "Artists": track?.artists.map(artist => artist.name).join(", "),
    "Disc Number": track?.disc_number,
    "Explicit": track?.explicit,
    "Spotify Api Link": track?.href,
    "Popularity": track?.popularity,
    "Spotify Id": track?.id,
    "Album": track?.album.name,
    "Track Number": track?.track_number
  }

</script>

<span>{albumStruct.toString()}</span>