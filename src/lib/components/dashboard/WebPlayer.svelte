<script lang="ts">
  import { Spotify } from "$lib/data/spotify";
  import { nextPlayback, pausePlayback, previousPlayback, startPlayback } from "$lib/services/spotify/api/player";
  import type { PlaybackState } from "$lib/types/spotify";
  import { msToTime } from "$lib/utils/converter/date-time";
  import { onDestroy } from "svelte";
  import ImageWithFallback from "../common/ImageWithFallback.svelte";
  import Svg from "../common/Svg.svelte";
  import ProgressBar from "../common/ProgressBar.svelte";
  
  const svgSize = "3.5rem";

  let playbackState: PlaybackState | null;

  $: timeMs = playbackState?.progress_ms || 0;
  $: lengthMs = playbackState?.item?.duration_ms || 1;
  $: remainingMs = lengthMs - timeMs;
  $: percent = ((timeMs / lengthMs) * 100).toFixed(1);

  (async () => {
    playbackState = await Spotify.Player.getPlayback();
  })();

  const playbackInterval = setInterval(async () => {
    playbackState = await Spotify.Player.getPlayback();    
  }, 1000);

  onDestroy(() => {
    clearInterval(playbackInterval);
  })
</script>

{#key playbackState}
  {#if playbackState}   
    <div class="player">

      <div class="header">
        <ImageWithFallback type={playbackState.item?.album} fallbackSvg="album"/>
        <div class="infos">
          <h2 class="title">{playbackState.item?.name}</h2>
          <h5  class="artists">{playbackState.item?.artists.map((a) => a.name).join(', ')}</h5>
          <small class="device">{playbackState.device.name}</small>          
        </div>      
      </div>

      <div class="progress">        
        <small class="time">{msToTime(timeMs)}</small>
        <small class="remaining-time">{msToTime(remainingMs)}</small>
        <ProgressBar max={lengthMs} value={timeMs}></ProgressBar>
        <small class="length">{msToTime(lengthMs)}</small>
        <small class="percent">{percent}%</small>
      </div>

      <div class="media-control">
        <button class="media-control-button" on:click={() => previousPlayback()}>
          <Svg name=previous class="svg" width={svgSize} height={svgSize}></Svg>
        </button>            
        {#if playbackState.is_playing}
          <button class="media-control-button" on:click={() => pausePlayback()}>
            <Svg name=pause class="svg" width={svgSize} height={svgSize}></Svg>
          </button>
        {:else}
          <button class="media-control-button" on:click={() => startPlayback()}>
            <Svg name=play class="svg" width={svgSize} height={svgSize}></Svg>
          </button>          
        {/if} 
        <button class="media-control-button" on:click={() => nextPlayback()}>
          <Svg name=next class="svg" width={svgSize} height={svgSize}></Svg>  
        </button>
      </div>
      
    </div>      
  {/if}
{/key}  



<style lang="scss">
  .player {
    aspect-ratio: 4/2;
    width: 28rem;
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

        .device {
          color: var(--text-subdued);
        }
      }
    }

    .progress {
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: space-between;
      gap: 1rem;

      .remaining-time, .percent, .time, .length {
        width: 10%;
      }
      .remaining-time, .percent {
        display: none;
      }
    }

    .progress:hover {
      .remaining-time, .percent {
        display: block;
      }

      .time, .length {
        display: none;
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
        fill: var(--text-subdued);
      }

      .media-control-button:hover {
        background-color: var(--background-elevated-highlight);
        cursor: pointer;        
        fill: var(--text-base);      
      }

      :global(.svg) {
        fill: inherit;
      }
      
    }
  }
</style>