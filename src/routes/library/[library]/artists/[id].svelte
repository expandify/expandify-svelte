<script lang="ts">
  import type {Artist} from "$lib/types/Artist";

  export let artist: Artist


  const albumStruct = {
    "Name": artist?.name,
    "Followers": artist?.followers?.total,
    "Genres": artist?.genres,
    "Spotify Api Link": artist?.href,
    "Images": artist?.images,
    "Popularity": artist?.popularity,
    "Spotify Id": artist?.id
  }

</script>

<span>{albumStruct.toString()}</span>