<script>
  import {STORE_STATUS, userStore} from "../../../stores/library.js";
  import {onMount} from "svelte";
  import {fetchItems} from "../../../lib/client/api-connection/request.js";

  export let user = null

  onMount(() => {
    if ($userStore.status !== STORE_STATUS.READY) {
      return
    }
    userStore.update(curr => ({user: curr.user, status: STORE_STATUS.FETCHING}))
    let data = user != null ? user : fetchItems("/library/user/__data.json");
    userStore.update(curr => {
      return ({user: data, status: curr.status});
    })
    userStore.update(curr => ({user: curr.user, status: STORE_STATUS.FINISHED}))
  })

</script>


{#if $userStore.status !== "finished"}
  <progress class="progress is-large is-primary" max="100"></progress>
{:else}
  <div class="box is-flex is-flex-direction-column">
    <div class="columns">
      <strong class="column is-one-fifth">Name: </strong> <div class="column">{$userStore.user.display_name}</div>
    </div>
    <div class="columns">
      <strong class="column is-one-fifth">Followers: </strong><div class="column">{$userStore.user.followers}</div>
    </div>
    <div class="columns">
      <strong class="column is-one-fifth">Country: </strong><div class="column">{$userStore.user.country}</div>
    </div>
    <div class="columns">
      <strong class="column is-one-fifth">Product: </strong><div class="column">{$userStore.user.product}</div>
    </div>
    <div class="columns">
      <strong class="column is-one-fifth">Spotify Id: </strong><div class="column">{$userStore.user.id}</div>
    </div>
  </div>
{/if}

<style lang="scss">

</style>