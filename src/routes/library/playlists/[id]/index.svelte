<script>
  import InfoView from "../../../../lib/components/elements/InfoView.svelte";
  import {onMount} from "svelte";
  import {page} from "$app/stores";
  import {fetchPagedItems} from "../../../../lib/api-connection/request.js";
  export let playlist = {}


  const playlistStruct = {
    "Name": playlist.name,
    "Description": playlist.description,
    "Owner": playlist.owner.display_name,
    "Public": playlist.public,
    "Followers": playlist.followers.total,
    "Collaborative": playlist.collaborative,
    "Spotify Link": playlist.href,
    "Spotify Id": playlist.id,
    "Snapshot Id": playlist.snapshot_id,
    "Images": playlist.images,
    "Primary Color": playlist.primary_color,
    "Tracks": playlist.tracks.items.map(track => track.track.name).join(", ")
  }

  onMount(async () => {
    let tracks = []
    const transform = data => data.playlistTracks
    const callback = (data) => tracks.push.apply(tracks, data.items)
    const path = `/library/playlists/${$page.params.id}/tracks`
    await fetchPagedItems(path, callback, transform, 50, {playlistTracks: playlist.tracks})
    playlistStruct.Tracks = tracks.map(track => track.track.name).join(", ")
  })

</script>

<InfoView infoStruct="{playlistStruct}"/>