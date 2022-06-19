<script lang="ts">
  import type {Album} from "$lib/types/Album";

  export let album: Album


  const albumStruct = {
    "Album type": album?.album_type,
    "Artists": album?.artists.map(artist => artist.name).join(", "),
    "Available Markets": album?.available_markets,
    "Genres": album?.genres,
    "Spotify Api Link": album?.href,
    "Images": album?.images,
    "Name": album?.name,
    "Popularity": album?.popularity,
    "Release Date": album?.release_date,
    "Release Date Precision": album?.release_date_precision,
    "Total Tracks": album?.total_tracks,
    "Tracks": album?.tracks.map(track => track.name).join(", ")
  }

</script>

<span>{albumStruct}</span>