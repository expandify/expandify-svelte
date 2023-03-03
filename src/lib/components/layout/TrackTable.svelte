<script lang="ts">
    import ImageWithFallback from "$lib/components/common/ImageWithFallback.svelte";
    import type { Track } from "$lib/types/spotify";
    import { formateDate, msToTime } from "$lib/utils/converter/date-time";

    export let tracks: (Track & {added_at?: string})[];
    export let showImage: boolean = true;
    export let showAddedAt: boolean = false;


</script>

<div class="table">
  <div class="row title-row">
    <span class="position">#</span>  
    {#if showImage}
      <div class="image-box">TITLE</div>
      <div class="title-box"></div>
    {:else}
      <div class="title-box">TITLE</div>
    {/if}      
    <span class="album">ALBUM</span>
    {#if showAddedAt}
      <span class="date">DATE ADDED</span>
    {/if}  
    <span class="time">TIME</span>
  </div>

  {#each tracks as track, i}
    <div class="row">
      <span class="position">{i + 1}</span>  
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
      {#if showAddedAt}
        <span class="date">{formateDate(track.added_at)}</span>
      {/if}  
      <span class="time overflow">{msToTime(track.duration_ms)}</span>
    </div>  
  {/each} 
</div>



<style lang="scss">
  .table {
    
    .row {     
      color: var(--text-subdued);      
      width: 100%;
      display: flex;    
      flex-direction: row;
      align-items: center;
      gap: 1rem;
      height: 3rem;
      border-radius: 0.4rem;
      padding: 0.5rem 0;

      .position {      
        min-width: max(2rem, 2%);
        max-width: max(2rem, 2%);
        display: flex;
        flex-direction: row;
        justify-content: flex-end;
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
        min-width: 7rem;
        max-width: 7rem;
      }

      .time {
        min-width: max(5rem, 4%);
        max-width: max(5rem, 4%); 
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
  }
</style>