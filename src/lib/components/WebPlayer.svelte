<script lang="ts">
  import { Spotify } from "$lib/data/spotify";
  import { nextPlayback, pausePlayback, previousPlayback, startPlayback } from "$lib/services/spotify/api/player";
  import type { PlaybackState } from "$lib/types/spotify";
  import { onDestroy } from "svelte";
    import AlbumImage from "./images/AlbumImage.svelte";
    import Svg from "./images/Svg.svelte";
  import ProgressBar from "./ProgressBar.svelte";
  
  let playbackState: PlaybackState;

  (async () => {
    playbackState = await Spotify.Player.getPlayback();
  })();

  const playbackInterval = setInterval(async () => {
    playbackState = await Spotify.Player.getPlayback();    
  }, 1000);

  function playbackPercent() {
    if (!playbackState.item || !playbackState.progress_ms) { return 0; }
    return (( playbackState.progress_ms / playbackState.item?.duration_ms) * 100).toFixed(2);
  }

  onDestroy(() => {
    clearInterval(playbackInterval);
  })
</script>

{#key playbackState}
  {#if playbackState}   
    <div class="player">

      <div class="header">
        <AlbumImage album={playbackState.item?.album}></AlbumImage>
        <div class="infos">
          <h2 class="title">{playbackState.item?.name}</h2>
          <h5  class="artists">{playbackState.item?.artists.map((a) => a.name).join(', ')}</h5>
        </div>      
      </div>

      <ProgressBar max={playbackState.item?.duration_ms} value={playbackState.progress_ms}></ProgressBar>

      <div class="media-control">
        <button class="media-control-button" on:click={() => previousPlayback()}>
          <Svg name=previous class="svg"></Svg>
        </button>            
        {#if playbackState.is_playing}
          <button class="media-control-button" on:click={() => pausePlayback()}>
            <Svg name=pause class="svg"></Svg>
          </button>
        {:else}
          <button class="media-control-button" on:click={() => startPlayback()}>
            <Svg name=play class="svg"></Svg>
          </button>          
        {/if} 
        <button class="media-control-button" on:click={() => nextPlayback()}>
          <Svg name=next class="svg"></Svg>  
        </button>
      </div>
      
    </div>
    
    <div class="artists"><small>{playbackState.device.name}</small></div>
    <div class="artists"><small>{playbackPercent()}%</small></div>
    <div class="artists"><small>{playbackState.item?.artists.map((a) => a.name).join(', ')}</small></div>

    <button class="play-pause" on:click={() => {playbackState.is_playing ? pausePlayback() : startPlayback()}}>Play/Pause </button>
    <ProgressBar max={playbackState.item?.duration_ms} value={playbackState.progress_ms}></ProgressBar>

    <button class="prev" on:click={() => previousPlayback()}>Previous</button>
    <button class="next" on:click={() => nextPlayback()} >Next</button>
  {/if}
{/key}  



<style lang="scss">
  .player {
    aspect-ratio: 4/2;
    height: 100%;
    border-radius: 2rem;
    background-color: var(--background-elevated-base);
    padding: 2rem;
    padding-bottom: 1rem;
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    .header {
      display: flex;
      flex-direction: row;
      height: 50%;
      gap: 1rem;

      .infos {
        display: flex;
        flex-direction: column; 

        .title {
          margin-top: 0;
        }

        .artists {
          margin-top: 0;
          color: var(--text-subdued);
        }
      }
    }

    .media-control {
      display: flex;
      flex-direction: row; 
      justify-content: space-between;      

      .media-control-button {
        background-color: inherit;
        border-radius: 5rem;
        border: none;
        padding: 1rem;
        fill: var(--text-subdued);
      }

      .media-control-button:hover {
        background-color: var(--background-elevated-highlight);
        cursor: pointer;        
        fill: var(--text-base);      
      }

      :global(.svg) {
        fill: inherit;
        width: 2.5rem;
        height: 2.5rem;
      }
      
    }
  }
</style>