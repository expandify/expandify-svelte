<script>
  import {page} from '$app/stores';
  import {albums} from "../../../stores/library.js";
  import {goto} from "$app/navigation";
  import {onMount} from "svelte";

  const id = $page.params.id
  const album = $albums.items.find(value => value.album.id === id)

  onMount(() => {
    if (album === undefined) {
      goto("/library/albums")
    }
  })

  const albumStruct = {
    "Album type": album.album.album_type,
    "Artists": album.album.artists,
    "Available Markets": album.album.available_markets,
    "Copyright": album.album.copyright,
    "Genres": album.album.genres,
    "Spotify Api Link": album.album.href,
    "Images": album.album.images,
    "Label": album.album.lable,
    "Name": album.album.name,
    "Popularity": album.album.popularity,
    "Release Date": album.album.release_date,
    "Release Date Precision": album.album.release_date_precision,
    "Total Tracks": album.album.total_tracks,
    "Tracks": album.album.tracks
  }
  const albumInfo = Object.entries(albumStruct)

</script>

<div class="rows">
  {#each albumInfo as [key, value]}
    <div class="cols">
      <div class="key">{key}</div>
      <div class="value">{value}</div>
    </div>
  {/each}
</div>

<style lang="scss">

  .rows {
    display: flex;
    flex-direction: column;
    gap: 2rem;

    .cols {
      display: flex;
      flex-direction: row;

      .key {
        width: 20rem;
        font-weight: bold;
        color: var(--accent);
      }

      .value {
        overflow-wrap: break-word;
        width: 40rem;
      }
    }
  }
</style>