<script lang="ts">
  import {goto} from "$app/navigation";
  import type {TableTrack} from "../../../shared/types/TableTrack";

  export let hrefBasePath: string | null = null
  export let tracks: TableTrack[] = []
  export let search: string = ""
  let reactiveTracks: TableTrack[]
  $: reactiveTracks = searchTracks(search, tracks)

  function searchTracks(filter: string, toFilter: TableTrack[]) {
    let inSearch = (str: string) => str.toLowerCase().includes(filter.toLowerCase())

    return toFilter.filter(s => inSearch(s.name) || inSearch(s?.album.name) || s?.artists?.some(a => inSearch(a.name)));
  }

  function gotoId(id: string) {
    if (hrefBasePath !== null && id) {
      goto(`${hrefBasePath}/${id}`)
    }
  }

</script>

<div class="table">
  <div class="header row">
    <div class="cell title-col">Title</div>
    <div class="cell album-col">Album</div>
    <div class="cell date-col">Date Added</div>
    <div class="cell duration-col">Duration</div>
  </div>
  {#each reactiveTracks as track}
    <div class="row" on:click={() => gotoId(track.id)}>
      <div class="cell title-col">
        <img src="{track.image}" class="image" alt="{track.name}">
        <div class="title">
          <span class="name">{track.name}</span>
          <span class="artist">{track.artists}</span>
        </div>
      </div>
      <div class="cell album-col">{track.album}</div>
      <div class="cell date-col">{track.added_at}</div>
      <div class="cell duration-col">{track.duration}</div>
    </div>
  {/each}
</div>


<style lang="scss">

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

