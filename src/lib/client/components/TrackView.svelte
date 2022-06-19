<script lang="ts">
  import {goto} from "$app/navigation";
  import type {TableTrack} from "$lib/types/TableTrack";
  import {fade} from "svelte/transition";
  import ComparingDiv from "./ComparingDiv.svelte";

  export let hrefBasePath: string | null = null
  export let tracks: TableTrack[] = []
  export let search: string = ""
  let delayedSearch = search

  $: setTimeout(() => delayedSearch = search, 300)

  let reactiveTracks: TableTrack[]
  $: reactiveTracks = searchTracks(delayedSearch)

  function searchTracks(filter: string) {
    filter = filter.toLowerCase()
    const filteredTracks = []
    for (let i = 0; i < tracks.length; i++) {
      if (tracks[i].name_lower_case.indexOf(filter) !== -1 ||
        tracks[i].artists_joined_lower_case.indexOf(filter) !== -1 ||
        tracks[i].album_lower_case.indexOf(filter) !== -1) {
        filteredTracks.push(tracks[i])
      }
    }
    return filteredTracks
  }

  function gotoId(id: string) {
    if (hrefBasePath !== null && id) {
      goto(`${hrefBasePath}/${id}`)
    }
  }

</script>

<div class="table" in:fade>
  <div class="header row">
    <div class="cell title-col">Title</div>
    <div class="cell album-col">Album</div>
    <div class="cell date-col">Date Added</div>
    <div class="cell duration-col">Duration</div>
  </div>
  {#each reactiveTracks as track}
    <div class="row" on:click={() => gotoId(track.id)}>
      <ComparingDiv libraryId={track.libraryId}>
        <div class="cell title-col">
        <img src="{track.image}" class="image" alt="{track.name}">
        <div class="title">
          <span class="name">{track.name}</span>
          <span class="artist">{track.artists_joined}</span>
        </div>
      </div>
      </ComparingDiv>
      <div class="cell album-col">{track.album.name}</div>
      <div class="cell date-col">{track.added_at}</div>
      <div class="cell duration-col">{track.duration}</div>
    </div>
  {/each}
</div>


<style lang="scss">

  .table {
    display: flex;
    flex-direction: column;

    .header {
      border-bottom: 0.1rem solid var(--bg-main-50);
    }

    .row {
      height: 3rem;
      display: flex;
      flex-direction: row;
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
      border: 0.1rem solid rgba(0,0,0,0);
    }

    .row:hover:not(.header) {
      border: 0.1rem solid var(--accent);
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

