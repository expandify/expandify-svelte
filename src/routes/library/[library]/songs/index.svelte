<script>
  import {formatDate, imageSelector, msToTime} from "../../../../lib/shared/helpers";
  import SearchBar from "../../../../lib/client/components/elements/SearchBar.svelte";
  import {goto} from "$app/navigation";
  import {page} from "$app/stores";
  import {songStore} from "../../../../lib/client/stores/library";


  export let items = []
  export let last_updated
  let search = ""
  $songStore.songs = items
  $: songs = parseSongs($songStore.songs)

  function parseSongs(songs) {
    return songs.map(value =>
        ({
          name: value.name,
          artists: value.artists.map(artist => artist.name).join(),
          album: value.album.name,
          added_at: formatDate(value.added_at),
          duration: msToTime(value.duration_ms),
          image: imageSelector(value.album.images),
          id: value.id
        }))
  }

  let reactiveSongs
  $: reactiveSongs = searchSongs(search, songs)

  function searchSongs(filter, songs) {
    let inSearch = (str) => str.toLowerCase().includes(filter.toLowerCase())

    return songs.filter(s => inSearch(s.name) || inSearch(s.artists) || inSearch(s.album));
  }

  function gotoId(id) {
    let gotoPath = $page.url.href
    if (gotoPath !== null && id) {
      goto(`${gotoPath}/${id}`)
    }
  }

</script>

<div class="top">
  <h1>Songs</h1>
  {#if last_updated !== null}
    <span>Last Updated: {formatDate(last_updated)}</span>
  {/if}
  <SearchBar searchIn="songs" bind:search={search}/>
</div>

<div class="table">
  <div class="header row">
    <div class="cell title-col">Title</div>
    <div class="cell album-col">Album</div>
    <div class="cell date-col">Date Added</div>
    <div class="cell duration-col">Duration</div>
  </div>
  {#each reactiveSongs as song}
    <div class="row" on:click={() => gotoId(song.id)}>
      <div class="cell title-col">
        <img src="{song.image}" class="image" alt="image">
        <div class="title">
          <span class="name">{song.name}</span>
          <span class="artist">{song.artists}</span>
        </div>
      </div>
      <div class="cell album-col">{song.album}</div>
      <div class="cell date-col">{song.added_at}</div>
      <div class="cell duration-col">{song.duration}</div>
    </div>
  {/each}

</div>


<style lang="scss">

  .top {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
  }

  .table {
    display: flex;
    flex-direction: column;
    margin-left: auto;
    margin-right: auto;

    .header {
      position: sticky;
      top: 3.5rem;
      background-color: var(--bg-main-100);
      border-bottom: 0.2rem solid var(--accent);

      .cell {
      }
    }

    .row {
      height: 3rem;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;


      .cell {
        padding: 0.5rem;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
      }
    }

    .row:not(.header) {
      margin-top: 0.5rem;
      padding: 0.5rem 0 0.5rem 0;
    }
    .row:hover:not(.header) {
      background-color: var(--bg-main-100);
      border-radius: 0.5rem;
      cursor: pointer;
    }

    .title-col {
      width: 50%;
      height: 100%;
      display: flex;
      flex-direction: row;
      align-items: center;

      .image {
        height: 100%;
        object-fit: cover;
        aspect-ratio: 1/1;
        margin-right: 1rem;
      }

      .title {
        display: flex;
        flex-direction: column;
        gap: 0.5rem;
        overflow: hidden;
        .name {
          text-overflow: ellipsis;
          overflow: hidden;
          white-space: nowrap;
        }
        .artist {
          text-overflow: ellipsis;
          overflow: hidden;
          white-space: nowrap;
          font-size: small;
        }
      }
    }

    .album-col {
      width: 25%;
    }

    .date-col {
      width: 18%;
    }

    .duration-col {
      width: 7%;
    }
  }

</style>

