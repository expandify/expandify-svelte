<script>
  import InfoView from "../../../../lib/components/elements/InfoView.svelte";
  import {onMount} from "svelte";
  import {page} from "$app/stores";
  import {fetchPagedItems} from "../../../../lib/api-connection/request.js";
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

  onMount(async () => {
    let tracks = []
    const transform = data => data.albumTracks
    const callback = (data) => tracks.push.apply(tracks, data.items)
    const path = `/library/albums/${$page.params.id}/tracks`
    await fetchPagedItems(path, callback, transform, 50, {albumTracks: album.tracks})
    albumStruct.Tracks = tracks.map(track => track.name).join(", ")
  })
</script>

<InfoView infoStruct="{albumStruct}"/>