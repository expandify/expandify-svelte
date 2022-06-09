<script>
  import InfoView from "../../../../../lib/client/components/elements/InfoView.svelte";
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

</script>

<InfoView infoStruct="{playlistStruct}"/>