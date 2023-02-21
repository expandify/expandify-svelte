<script lang="ts">
	import type { SavedTrack } from "$lib/types/spotify";
	import { formateDate, msToTime } from "$lib/utils/converter/date-time";  
	import AlbumImage from "../images/AlbumImage.svelte";

  export let tracks: SavedTrack[];

  let firstVisibleTrack = 0;
  let visibleRows = 0;
  let lastVisibleTrack = 0;

  let innerHeight: number;
  let lastUpdatedScrollPosition: number;
  let scroll: number;
  $: scroll, innerHeight, tracks, setVisibleIndex()


  function setVisibleIndex() {
    const rowHeight = document.querySelector(".content-row")?.clientHeight || document.querySelector(".header-row")?.clientHeight!;
    
    if (scroll != 0 && Math.abs(scroll - lastUpdatedScrollPosition) < rowHeight) {
      return;
    }
    lastUpdatedScrollPosition = scroll;

    const headerHeight = (document.querySelector(".content-row")?.getBoundingClientRect().top || rowHeight) + scroll;
    const scrollOffset = (Math.max(0, scroll - headerHeight));

    const firstRow = Math.floor(scrollOffset / rowHeight);
    visibleRows = Math.ceil(innerHeight / rowHeight);
    const lastRow = firstRow + visibleRows + 1; // +1 to include partialy visible rows

    firstVisibleTrack = Math.floor(Math.max(0, firstRow) );
    lastVisibleTrack = Math.ceil(Math.min(tracks.length, lastRow)    );
    
  }

  function indexInView(i: number): boolean {
    if (i > firstVisibleTrack - 5 && i < lastVisibleTrack + 5) {
      return true;
    }
    return false;
  }
</script>

<svelte:window bind:scrollY={scroll} bind:innerHeight={innerHeight}/>
<div class="rows">
  <div class="row header-row">
    <span class="col-position">#</span>
    <span class="col-title">TITLE</span>
    <span class="col-image"></span>    
    <span class="col-album">ALBUM</span>
    <span class="col-date">DATE ADDED</span>
    <span class="col-time">TIME</span>
  </div>
  {#key firstVisibleTrack}
  {#each tracks as track, i}
  {#if indexInView(i)}
  <div class="row content-row">
    <span class="col-position">{i + 1}</span>
    <AlbumImage album={track.album} borderRadius="0"/>
    <div class="title-box overflow">      
      <span class="title overflow">{track.name}</span> 
      <span class="artists">{track.artists.map(a => a.name).join(", ")}</span> 
    </div>        
    <span class="col-album overflow">{track.album?.name}</span>
    <span class="col-date">{formateDate(track?.added_at)}</span>
    <span class="col-time">{msToTime(track.duration_ms)}</span>
  </div> 
  {:else}
  <div class="row content-row"></div>
  {/if}          
  {/each}  
  {/key}
</div>

<style lang="scss">
  .rows {
    color: var(--text-subdued);    
    font-weight: 400;
    min-width: 35rem;

    .row {      
      display: grid;    
      grid-template-columns: [index] 2rem [image] 3rem [title] 3fr [album] 2fr [date] 7rem [time] 3rem; 
      align-items: center;      
      gap: 1rem;
      height: 3rem;
      border-radius: 0.4rem;
      padding: 0.5rem;

      .title-box {
        display: flex;
        flex-direction: column;
        
        .title {
          font-weight: 500;
          color: var(--text-base);
        }

      }

      .overflow {
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
      }      
    }

    .content-row:hover {
      background-color: var(--background-elevated-highlight);
      cursor: pointer;
    }

    

    :global(.time) {
      width: 1.2rem;
    }
  }
</style>