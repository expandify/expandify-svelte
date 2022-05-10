<script>
  import {albums} from "../../stores/albums.js";

  console.log($albums)
  function artistsToString(artists) {
    return artists.map(artist => artist.name).join()
  }
</script>



{#if $albums.status !== "finished"}
  {#if $albums.status === "fetching"}
    <progress class="progress is-large is-primary" value="{$albums.items.length}" max="{$albums.total}"></progress>
    <h1 class="has-text-primary has-text-weight-bold is-size-4">Loading Albums: {$albums.items.length} of {$albums.total}</h1>
  {:else}
    <progress class="progress is-large is-primary" max="100"></progress>
  {/if}
{/if}


<table class="table">
  <thead>
  <tr>
    <th>Name</th>
    <th>Artists</th>
    <th>Label</th>
    <th>Tracks</th>
  </tr>
  </thead>
  <tbody>
  {#each $albums.items as album}
    <tr>
      <th>{ album.album.name }</th>
      <th>{ artistsToString(album.album.artists) }</th>
      <th>{ album.album.label }</th>
      <th>{ album.album.total_tracks }</th>
    </tr>
  {/each}
  </tbody>
</table>


<style lang="scss">

</style>