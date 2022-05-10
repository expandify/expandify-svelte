<script>
  import {playlists} from "../../stores/playlists.js";

</script>



{#if $playlists.status !== "finished"}
  {#if $playlists.status === "fetching"}
    <progress class="progress is-large is-primary" value="{$playlists.items.length}" max="{$playlists.total}"></progress>
    <h1 class="has-text-primary has-text-weight-bold is-size-4">Loading Playlists: {$playlists.items.length} of {$playlists.total}</h1>
  {:else}
    <progress class="progress is-large is-primary" max="100"></progress>
  {/if}
{/if}


<table class="table">
  <thead>
  <tr>
    <th>Name</th>
    <th>Id</th>
    <th>Owner</th>
    <th>Public</th>
    <th>Collaborative</th>
    <th>Tracks</th>
  </tr>
  </thead>
  <tbody>
  {#each ($playlists).items as playlist}
    <tr>
      <th>{ playlist.name }</th>
      <th>{ playlist.id }</th>
      <th>{ playlist.owner.display_name }</th>
      <th>{ playlist.public }</th>
      <th>{ playlist.collaborative }</th>
      <th>{ playlist.tracks.total }</th>
    </tr>
  {/each}
  </tbody>
</table>


<style lang="scss">

</style>