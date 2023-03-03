<script lang="ts">
  import ImageWithFallback from "$lib/components/common/ImageWithFallback.svelte";
  import type { Track } from "$lib/types/spotify";
  import { formateDate, msToTime } from "$lib/utils/converter/date-time";

  export let track: Track | null;
  export let addedAt: string | null = null;
  export let showImage: boolean = true;
  export let index: number | null = null;
  
</script>

{#if !track}

<div class="row title-row">
  <span class="position">#</span>  
  {#if showImage}
    <div class="image-box">TITLE</div>
    <div class="title-box"></div>
  {:else}
    <div class="title-box">TITLE</div>
  {/if}      
  <span class="album">ALBUM</span>
  {#if addedAt}
    <span class="date">DATE ADDED</span>
  {/if}  
  <span class="time">TIME</span>
</div>

{:else}  

<div class="row overflow">
  <span class="position">{index || track.track_number}</span>  
  {#if showImage}
    <div class="image-box">
      <ImageWithFallback type={track.album} borderRadius="0"/>  
    </div>
  {/if}    
  <div class="title-box overflow">      
    <span class="title overflow">{track.name}</span> 
    <span class="artists overflow">{track.artists.map(a => a.name).join(", ")}</span> 
  </div>        
  <span class="album overflow">{track.album?.name}</span>
  {#if addedAt}
    <span class="date">{formateDate(addedAt)}</span>
  {/if}  
  <span class="time">{msToTime(track.duration_ms)}</span>
</div>

{/if}



<style lang="scss">
  .row {     
    color: var(--text-subdued);    
    font-weight: 400;
    width: 100%; 
    display: flex;    
    flex-direction: row;
    align-items: center; 
    gap: 1rem;
    height: 3rem;
    border-radius: 0.4rem;
    padding: 0.5rem;

    .position {      
      min-width: 1rem;
      max-width: 1rem;
    }

    .image-box {      
      min-width: 3rem;
      max-width: 3rem;
    }

    .title-box {
      display: flex;
      flex-direction: column;
      margin-right: auto;
      min-width: 10rem;
      .title {
        font-weight: 500;
        color: var(--text-base);
      }
    }

    .album {      
      margin-right: 15%;      
      min-width: 15%;
      max-width: 15%;
    }

    .date {
      min-width: 4rem;
      max-width: 4rem;
    }

    .time {
      min-width: 3rem; 
      max-width: 3rem; 
    }
    
    .overflow {
      text-overflow: ellipsis;
      overflow: hidden;
      white-space: nowrap;
    }      
  }

  .row:hover {
    background-color: var(--background-elevated-highlight);
    cursor: pointer;
  }

  .title-row {
    padding-bottom: 0;
  }

  .title-row:hover {
    background-color: inherit;
    cursor: unset;
  }
</style>