<script lang="ts">
	import type { Album } from "$lib/types/spotify";
	import { msToTime } from "$lib/utils/converter/date-time";  
	
  export let album: Album;
  
  const tracks = album.tracks.sort((t1, t2) => {
    return t1.disc_number - t2.disc_number || t1.track_number - t2.track_number
  })
  const multiDisc = tracks.find((t) => t.disc_number > 1);
</script>

<div class="rows">
  <div class="row header-row">
    <span class="col-position">#</span>
    <span class="col-title">TITLE</span>
    <span class="col-plays">PLAYS</span>
    <span class="col-time">TIME</span>
  </div>
  {#each tracks as track}
  <div class="row content-row">
    <span class="col-position">{track.track_number}</span>
    <div class="title-box overflow">      
      <span class="title overflow">{track.name}</span> 
      <span class="artists">{track.artists.map(a => a.name).join(", ")}</span> 
    </div>        
    <span class="col-plays">{track.type}</span>
    <span class="col-time">{msToTime(track.duration_ms)}</span>
  </div>       
  {/each}  
</div>

<style lang="scss">
  .rows {
    color: var(--text-subdued);    
    font-weight: 400;
    min-width: 35rem;

    .row {      
      display: grid;    
      grid-template-columns: [index] 2rem [title] 3fr [plays] 2fr [time] 3rem; 
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

  
  }
</style>