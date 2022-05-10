<script>
  import {user} from "../../stores/user.js";

</script>


{#if $user.status !== "finished"}
  <progress class="progress is-large is-primary" max="100"></progress>
{:else}
  <div class="box is-flex is-flex-direction-column">
    <div class="columns">
      <strong class="column is-one-fifth">Name: </strong> <div class="column">{$user.user.display_name}</div>
    </div>
    <div class="columns">
      <strong class="column is-one-fifth">Followers: </strong><div class="column">{$user.user.followers}</div>
    </div>
    <div class="columns">
      <strong class="column is-one-fifth">Country: </strong><div class="column">{$user.user.country}</div>
    </div>
    <div class="columns">
      <strong class="column is-one-fifth">Product: </strong><div class="column">{$user.user.product}</div>
    </div>
    <div class="columns">
      <strong class="column is-one-fifth">Spotify Id: </strong><div class="column">{$user.user.id}</div>
    </div>
  </div>
{/if}

<style lang="scss">

</style>