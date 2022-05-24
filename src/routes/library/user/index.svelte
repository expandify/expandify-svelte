<script>
  import {STORE_STATUS, userStore} from "../../../stores/library.js";
  import {onMount} from "svelte";
  import InfoView from "../../../lib/components/elements/InfoView.svelte";

  export let user = {}

  onMount(() => {
    userStore.update(curr => ({user: user, status: STORE_STATUS.FINISHED}))
  })


  const userStruct = {
    "Name": user.display_name,
    "County": user.country,
    "Followers": user.followers.total,
    "Images": user.images,
    "Subscription": user.product,
    "Spotify Api Link": user.href,
    "Spotify Id": user.id
  }

</script>


<InfoView infoStruct="{userStruct}"/>

<style lang="scss">

</style>