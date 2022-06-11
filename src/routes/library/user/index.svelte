<script lang="ts">
  import type {SpotifyUser} from "$lib/shared/types/SpotifyUser";

  export let user: SpotifyUser


  const userStruct = {
    "Name": user?.display_name,
    "County": user?.country,
    "Followers": user?.followers?.total,
    "Images": user?.images,
    "Subscription": user?.product,
    "Spotify Api Link": user?.href,
    "Spotify Id": user?.id
  }

</script>

<span>{userStruct.toString()}</span>