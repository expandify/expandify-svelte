<script lang="ts">
  import type {Playlist} from "$lib/types/Playlist";

  export let playlist: Playlist


  const playlistStruct = {
    "Name": playlist?.name,
    "Description": playlist?.description,
    "Owner": playlist?.owner.name,
    "Public": playlist?.public,
    "Followers": playlist?.followers?.total,
    "Collaborative": playlist?.collaborative,
    "Spotify Link": playlist?.href,
    "Spotify Id": playlist?.id,
    "Snapshot Id": playlist?.snapshot_id,
    "Images": playlist?.images,
    "Tracks": playlist?.tracks.map(track => track.name).join(", ")
  }

</script>

<span>{playlistStruct.toString()}</span>